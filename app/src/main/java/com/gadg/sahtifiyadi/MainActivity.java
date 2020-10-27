package com.gadg.sahtifiyadi;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.database.DBManagerMessage;
import com.gadg.sahtifiyadi.login.LoginActivity;
import com.gadg.sahtifiyadi.login.SignupActivity;
import com.gadg.sahtifiyadi.login.addUtilisateur.AddUtilisateurActivity;
import com.gadg.sahtifiyadi.login.profiles.MainProfile;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.gadg.sahtifiyadi.message.messageBoit.messageBoit;
import com.gadg.sahtifiyadi.services.NbrMSGnonRead;
import com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities;
import com.gadg.sahtifiyadi.utilities.location.MyLocation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.DEFAULT_MESSAGE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.DEFAULT_USER_IMAGE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.DEFAULT_USER_NAME;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.DEFAULT_USER_TYPE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_IS_LOGIN;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_IS_PROFILE_EXIST;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_IS_USER_DONOR;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_NUMBER_MESSAGES_NON_READ;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_ADRESSES;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_AGE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_COMMUNE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_EMAIL;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_GRP;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_IMAGE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_NAME;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_PHONE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_TYPE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_USER_WILAYA;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PREFERENCE_NAME;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.firstTime;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getUserImage;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getUserName;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveData;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveDoctorInfo;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveEtablissementInffo;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.savePharmaInfo;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveUserInfo;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveViewPagerInscriptionPosition;
import static com.gadg.sahtifiyadi.utilities.tools.LOCATION_PERMISSION;
import static com.gadg.sahtifiyadi.utilities.tools.readAllData;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ProgressDialog mProgressDialog;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView nav_user, nav_user_profil;
    private ImageView nav_user_image;
    private AlertDialog alertDialog;


    private static final String TAG = "MainActivityAkramTest1";
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String NbrMsgNoRead = intent.getStringExtra("NbrMsgs");
            Toast.makeText(getBaseContext(),NbrMsgNoRead+" non lus",Toast.LENGTH_LONG).show();
        }
    };
    private String CHANNEL_ID;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.d(TAG, "Before setContent");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		Log.d(TAG, "Before floating bar");
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, messageBoit.class));
            }
        });
		Log.d(TAG, "Before drawer layout");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_don_de_sang, R.id.nav_medicament,R.id.nav_settings, R.id.nav_a_propos_de_nous, R.id.nav_partager, R.id.nav_contacter_nous)
                .setDrawerLayout(drawer)
                .build();
				Log.d(TAG, "Before NavController");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View hView = navigationView.getHeaderView(0);
        nav_user = (TextView) hView.findViewById(R.id.nav_user_name);
        nav_user_profil = (TextView) hView.findViewById(R.id.nav_user_profile);
        nav_user_image = (ImageView) hView.findViewById(R.id.nav_user_image);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Chargement");
        mProgressDialog.setMessage("S'il vous plait attendez un peu ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
        mProgressDialog.setIndeterminate(true);

        locationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);
        locationListener = new MyLocation(getBaseContext());
        checkPermissions( locationManager, locationListener);

        synchronizeLayout();

        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ServiceAkramTest","on creat new service");

                        startService(new Intent(MainActivity.this, NbrMSGnonRead.class));
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,2000);

        if (firstTime(this)) {
            new UpdateDataBaseTask().execute();
            Log.d("AkramTestdatabase","first Time");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(getBaseContext()).inflate(R.layout.custom_view, viewGroup, false);
            builder.setView(dialogView);
            alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            Button okBtn = (Button) alertDialog.findViewById(R.id.buttonOk);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        } else  Log.d("AkramTestdatabase","no first time");
    }

    public void synchronizeLayout() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            fab.setVisibility(View.VISIBLE);
            nav_user.setText(getUserName(getBaseContext()));
            Glide.with(this)
                    .load(getUserImage(getBaseContext()))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(nav_user_image);
            nav_user_profil.setText("Mon Profile");
            nav_user_profil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), MainProfile.class));
                }

            });
            Glide.with(this)
                    .load(getUserImage(getBaseContext()))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(nav_user_image);
        } else DefaultLayout();
    }

    public void DefaultLayout() {
        nav_user.setText("Sahti fi yedi");
        nav_user_profil.setText("Ajouter votre établissement");
            fab.setVisibility(View.GONE);
        nav_user_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignupActivity.class));
            }
        });
        Glide.with(this)
                .load(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(nav_user_image);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
      if (FirebaseAuth.getInstance().getCurrentUser()!= null){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }else {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_log_in) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.action_log_out) {
           new AlertDialog.Builder(this)
                    .setTitle("Avertissement")
                    .setMessage("Voullez-vous vraiment Sign out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mProgressDialog.show();
                            FirebaseAuth.getInstance().signOut();
                           SharedPreferencecUtilities.saveLoginState(getBaseContext(),false);
                            saveUserInformation(getBaseContext(), false);
                            DefaultLayout();
                            invalidateOptionsMenu();
                            DBManagerMessage db = new DBManagerMessage(getBaseContext());
                            db.open();
                            db.deleteAll();
                            db.close();
                           mProgressDialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
		Log.d(TAG, "Inside onSupportNavigateUp");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		Log.d(TAG, "Before return NavigationUi inside onSupportNavigateUp");
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void updateStatusBarColor(String color){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }

    }

    public void updateActionBarColorTWO(String color){
        ActionBar bar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(color));
        bar.setBackgroundDrawable(colorDrawable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    }
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveUserInformation(this, FirebaseAuth.getInstance().getCurrentUser() != null);
    }
    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gadg.sahtifiyadi");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }
    public void checkPermissions(LocationManager locationManager, LocationListener locationListener){


        int permissionLocation = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermission = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermission.isEmpty()){
                ActivityCompat.requestPermissions(MainActivity.this,listPermission.toArray(new String[listPermission.size()]),LOCATION_PERMISSION);
            }
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }


    public void saveUserInformation(final Context context, final Boolean isLogin) {
       DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
       DatabaseReference UsersRef = rootRef.child("Users");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UsersRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean mContinue = dataSnapshot.child("UserName").exists()
                            && dataSnapshot.child("UserImageUrl").exists()
                            && dataSnapshot.child("UserType").exists()

                            && dataSnapshot.child("UserEmail").exists()
                            && dataSnapshot.child("UserAge").exists()
                            && dataSnapshot.child("UserPhone").exists()

                            && dataSnapshot.child("UserAdress").exists()
                            && dataSnapshot.child("UserWillaya").exists()
                            && dataSnapshot.child("UserCommune").exists()
                            && dataSnapshot.child("UserDonnation").exists()
                            && dataSnapshot.child("UserGrp").exists();


                    Log.d("logingtest","mContinue User = "+ mContinue);

                    Log.d("logingtest","UserName = "+ dataSnapshot.child("UserName").exists());
                    Log.d("logingtest","UserImageUrl = "+  dataSnapshot.child("UserImageUrl").exists());
                    Log.d("logingtest","UserType = "+ dataSnapshot.child("UserType").exists());
                    Log.d("logingtest","UserEmail = "+ dataSnapshot.child("UserEmail").exists());
                    Log.d("logingtest","UserAge = "+ dataSnapshot.child("UserAge").exists());
                    Log.d("logingtest","UserPhone = "+ dataSnapshot.child("UserPhone").exists());
                    Log.d("logingtest","UserAdress = "+ dataSnapshot.child("UserAdress").exists());
                    Log.d("logingtest","UserWillaya = "+ dataSnapshot.child("UserWillaya").exists());
                    Log.d("logingtest","UserCommune = "+ dataSnapshot.child("UserCommune").exists());
                    Log.d("logingtest","UserDonnation = "+ dataSnapshot.child("UserDonnation").exists());
                    Log.d("logingtest","UserGrp = "+ dataSnapshot.child("UserGrp").exists());






                    if (mContinue) {
                        String Name = dataSnapshot.child("UserName").getValue(String.class);
                        String ImageUrl = dataSnapshot.child("UserImageUrl").getValue(String.class);
                        String Type = dataSnapshot.child("UserType").getValue(String.class);

                        String email = dataSnapshot.child("UserEmail").getValue(String.class);
                        String age = dataSnapshot.child("UserAge").getValue(String.class);
                        String phone = dataSnapshot.child("UserPhone").getValue(String.class);

                        String adress = dataSnapshot.child("UserAdress").getValue(String.class);
                        String willaya = dataSnapshot.child("UserWillaya").getValue(String.class);
                        String commune = dataSnapshot.child("UserCommune").getValue(String.class);
                        boolean isDonor = dataSnapshot.child("UserDonnation").getValue(boolean.class);
                        String grp = dataSnapshot.child("UserGrp").getValue(String.class);

                        Log.d("logingtest","profile exist");
                        saveData(context, Name, Type,email,age,phone,adress,willaya,commune, isDonor, grp,ImageUrl, true, true);
                        // getNbrMessageNoRead(context);
                        switch (Type){
                            case "0" :
                                break;
                            case "1":saveViewPagerInscriptionPosition(context, 1);
                                saveDoctorInfo(context);
                                break;
                            case "2":saveViewPagerInscriptionPosition(context, 2);
                                savePharmaInfo(context);
                                break;
                            case "3":saveViewPagerInscriptionPosition(context, 3);
                                saveEtablissementInffo(context,3);
                                break;
                            case "4":
                                saveViewPagerInscriptionPosition(context, 4);
                                saveEtablissementInffo(context,4);
                                break;

                        }


                    } else {
                        Log.d("logingtest","profile does not exist");
                        saveData(context, DEFAULT_USER_NAME, DEFAULT_USER_IMAGE, DEFAULT_USER_TYPE, "","","","","",false,"+O","R.drawale.profile",true, false);
                        saveViewPagerInscriptionPosition(context, 0);
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Rappel")
                                .setMessage("Vous ne complete pas votre compte !")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, AddUtilisateurActivity.class));
                                    }
                                })
                                .setNegativeButton("Plus tard", null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "error : " + databaseError.getMessage());
                }
            });
        } else {
            saveData(context, DEFAULT_USER_NAME, DEFAULT_USER_IMAGE, DEFAULT_USER_TYPE, "","","","","",false,"+O","R.drawale.profile",true, false);
            saveViewPagerInscriptionPosition(context, 0);

        }
    }



    public void saveData(Context context, String userName,String userType, String email, String age, String phone, String adress, String willaya, String commune,boolean isDonor,String grp,String userImage, boolean isLogin, boolean isProfileExist) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_IMAGE, userImage);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_USER_GRP, grp);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_AGE, age);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putString(KEY_USER_ADRESSES, adress);
        editor.putString(KEY_USER_WILAYA, willaya);
        editor.putString(KEY_USER_COMMUNE, commune);
        editor.putBoolean(KEY_IS_USER_DONOR, isDonor);

        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.putBoolean(KEY_IS_PROFILE_EXIST, isProfileExist);


        if (isLogin && isProfileExist) {
            String nbrDeMSG = DEFAULT_MESSAGE;
            editor.putString(KEY_NUMBER_MESSAGES_NON_READ, nbrDeMSG);
        }
        editor.apply();
        synchronizeLayout();
    }

    @Override
    protected void onStop() {

        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

    }
    public class UpdateDataBaseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            readAllData(getBaseContext());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
        }
    }

}