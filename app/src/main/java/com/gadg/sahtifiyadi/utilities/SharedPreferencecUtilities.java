package com.gadg.sahtifiyadi.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.gadg.sahtifiyadi.FragmentRefreshListener;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerMessage;
import com.gadg.sahtifiyadi.database.DBManagerSpeciality;
import com.gadg.sahtifiyadi.login.addUtilisateur.AddUtilisateurActivity;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.gadg.sahtifiyadi.message.messageBoit.messageBoit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SharedPreferencecUtilities {
    public static final String PREFERENCE_NAME = "MainPreference";
    public static final String KEY_IS_LOGIN = "_is_login";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String CITY = "city";
    public static final String FULL_ADRESS = "full_adress";
	    public static final String SPECIALITY = "speciality";
    public static final String VIEWPAGER_POSITION = "viewpager_position";

 // Les clés pour sauvgarder les information d'utilisateur

    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_IMAGE = "userImage";
    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_IS_PROFILE_EXIST = "IsProfileExist";
    public static final String KEY_NUMBER_MESSAGES_NON_READ = "NbrMessageNoRead";


    public static final String TAG = PreferenceUtilities.class.getSimpleName();


    public static final String DEFAULT_MESSAGE = "0";
    public static final String DEFAULT_USER_NAME = "Sahti fi yedi";
    public static final String DEFAULT_USER_TYPE = "NormalUser";
    public static final String DEFAULT_USER_IMAGE = String.valueOf(R.drawable.logo);
    public static final String FIRST_TIME = "firstTime";
    public static final String KEY_USER_EMAIL = "userEmail";
    public static final String KEY_USER_AGE = "userAge";
    public static final String KEY_USER_PHONE = "userPhone";
    public static final String KEY_USER_ADRESSES = "userAdresses";
    public static final String KEY_USER_WILAYA = "userWilaya";
    public static final String KEY_USER_COMMUNE = "userCommune";
    public static final String KEY_IS_USER_DONOR = "isDonor";
    public static final String DOCTOR_NAME = "doctorName";
    public static final String DOCTOR_IMAGE = "doctorImage";
    public static final String DOCTOR_TYPE = "doctorType";
    public static final String DOCTOR_SPECIALITY = "doctorSpeciality";
    public static final String DOCTOR_SERVICE = "doctorService";
    public static final String NUM_ORDRE = "numOrdre";
    public static final String DOCTOR_PHONE = "doctorPhone";
    public static final String DOCTOR_TIME = "doctorTime";
    public static final String DOCTOR_WILAYA = "doctorWilaya";
    public static final String DOCTOR_ADRESSE = "doctorAdresse";
    public static final String DOCTOR_COMMUNE = "doctorCommune";
    public static final String DOCTOR_EXIST = "doctorExist";
    public static final String PHARMA_NAME = "pharmaName";
    public static final String PHARMA_IMAGE = "pharmaImage";
    public static final String PHARMA_DESCRIPTION = "pharmaDescription";
    public static final String PHARMA_TIME = "pharmaTime";
    public static final String PHARMA_PHONE = "pharmaPhone";
    public static final String PHARMA_ADRESSE = "pharmaAdresse";
    public static final String PHARMA_WILAYA = "pharmaWilaya";
    public static final String PHARMA_COMMUNE = "pharmaCommune";
    public static final String PHARMA_EXIST = "pharmaExist";
    public static final String ETABLISSEMENT_NAME = "etablissementName";
    public static final String ETABLISSEMENT_IMAGE = "etablissementImage";
    public static final String ETABLISSEMENT_TYPE = "etablissementType";
    public static final String ETABLISSEMENT_DESCRIPTION = "etablissementDescription";
    public static final String ETABLISSEMENT_SERVICE = "etablissementService";
    public static final String ETABLISSEMENT_PHONE = "etablissementPhone";
    public static final String ETABLISSEMENT_TIME = "etablissementTime";
    public static final String ETABLISSEMENT_ADRESSE = "etablissementAdresse";
    public static final String ETABLISSEMENT_WILAYA = "etablissementWilaya";
    public static final String ETABLISSEMENT_COMMUNE = "etablissementCommune";
    public static final String ETABLISSEMENT_EXIST = "etablissementExist";
    public static final String KEY_USER_GRP = "userGrp";


    private static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference UsersRef = rootRef.child("Users");
    private static DatabaseReference MessageNonReadRef = rootRef.child("Messages");
    private static DatabaseReference specRef = rootRef.child("Specialities");


//
public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";



    private static Integer count = new Integer(0);

    public static void saveLoginState(Context context,Boolean state) {
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putBoolean(KEY_IS_LOGIN, state);
        editor.apply();
    }
    public static boolean isLogin(Context context){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static String getUserName(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(KEY_USER_NAME, DEFAULT_USER_NAME);
    }
    public static String getUserImage(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(KEY_USER_IMAGE, DEFAULT_USER_IMAGE);
    }

    public static void saveLocation(Context context,String laltitude, String longitude, String city, String fullAdress) {
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(LATITUDE, laltitude);
        editor.putString(LONGITUDE, longitude);
        editor.putString(CITY,city);
        editor.putString(FULL_ADRESS,fullAdress);
    }
    public static String getLaltitude(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(LATITUDE,"100");
    }
    public static String getLongitude(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(LONGITUDE,"100");
    }
    public static String getCity(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(CITY,"Sidi Bel Abbess");
    }
    public static String getFullAdrress(Context context){
        SharedPreferences mPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mPreference.getString(FULL_ADRESS,"Quartier Al Wiaam");
    }
	
	    public static void saveSpeciality(Context context, String speciality) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putString(SPECIALITY, speciality);
        editor.apply();
    }
	
	public static String getLastSpeciality(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getString(SPECIALITY, "Médecin généraliste");

    }
    public static void saveViewPagerInscriptionPosition(Context context, int position) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putInt(VIEWPAGER_POSITION, position);
        editor.apply();
    }
    public static int getLastViewPagerInscriptionPosition(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getInt(VIEWPAGER_POSITION, 0);
    }



 public static void saveUserInfo(final Context context, final Boolean isLogin) {

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


    public static void saveEtablissementInffo(final Context context, int n) {
         DatabaseReference etablissementRef = null;
         if (n == 3 ){
             etablissementRef =  rootRef.child("Hopitals");
         }else {
             etablissementRef =  rootRef.child("laboratoirs");
         }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            etablissementRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    boolean mContinue = ds.child("_ID_Firebase").exists()
                            && ds.child("ImageUrl").exists()
                            && ds.child("Name").exists()
                            && ds.child("Place").exists()
                            && ds.child("Phone").exists()
                            && ds.child("Wilaya").exists()
                            && ds.child("Commune").exists()
                            && ds.child("Description").exists()
                            && ds.child("Service").exists()
                            && ds.child("numOrdre").exists()
                            && ds.child("Type").exists()
                            && ds.child("Time").exists()
                            && ds.child("EtablissmentExist").exists();

                    Log.d("logingtest","mContinue etablissement = "+ mContinue);

                    Log.d("logingtest","_ID_Firebase = "+ ds.child("_ID_Firebase").exists());
                    Log.d("logingtest","Name = "+  ds.child("Name").exists());
                    Log.d("logingtest","Place = "+ ds.child("Place").exists());
                    Log.d("logingtest","Phone = "+ ds.child("Phone").exists());
                    Log.d("logingtest","Wilaya = "+ ds.child("Wilaya").exists());

                    Log.d("logingtest","Commune = "+ ds.child("Commune").exists());
                    Log.d("logingtest","Description = "+ ds.child("Description").exists());
                    Log.d("logingtest","Service = "+ ds.child("Service").exists());

                    Log.d("logingtest","numOrdre = "+ ds.child("numOrdre").exists());
                    Log.d("logingtest","Type = "+ ds.child("Type").exists());
                    Log.d("logingtest","Time = "+ ds.child("Time").exists());
                    Log.d("logingtest","EtablissmentExist = "+ ds.child("EtablissmentExist").exists());

                    if (mContinue) {
                        String Name = ds.child("Name").getValue(String.class);
                        String Place = ds.child("Place").getValue(String.class);
                        String Phone = ds.child("Phone").getValue(String.class);
                        String ImageUrl = ds.child("ImageUrl").getValue(String.class);
                        String wilaya = ds.child("Wilaya").getValue(String.class);
                        String commune = ds.child("Commune").getValue(String.class);
                        String description = ds.child("Description").getValue(String.class);
                        String service = ds.child("Service").getValue(String.class);
                        String numOrdre = ds.child("numOrdre").getValue(String.class);
                        String Type = ds.child("Type").getValue(String.class);
                        String time = ds.child("Time").getValue(String.class);
                        boolean isExist = ds.child("EtablissmentExist").getValue(boolean.class);


                        Log.d("logingtest","doctor exist");
                        saveEtablissementData(context, Name, Type,description,Place,wilaya,commune,time,Phone,service,numOrdre,isExist, ImageUrl);
                    } else {
                        Log.d("logingtest","profile does not exist");
                        saveEtablissementData(context, "", "0","","","0","0","","","","",false, "");
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Reminder")
                                .setMessage("vous ne completez pas votre Établissement profile!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, AddUtilisateurActivity.class));
                                    }
                                })
                                .setNegativeButton("Later", null)
                                .setIcon(android.R.drawable.stat_notify_error)
                                .show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "error : " + databaseError.getMessage());
                }
            });
        } else {
            saveEtablissementData(context, "", "0","","","0","0","","","","",false, "");
        }
    }

private static void saveEtablissementData(Context context, String name, String type, String description,
                                              String place, String wilaya, String commune,
                                              String time, String phone, String service, String numOrdre,
                                              boolean isExist, String imageUrl) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ETABLISSEMENT_NAME, name );
        editor.putString(ETABLISSEMENT_IMAGE, imageUrl);
        editor.putString(ETABLISSEMENT_TYPE, type);

        editor.putString(ETABLISSEMENT_DESCRIPTION, description);
        editor.putString(ETABLISSEMENT_SERVICE, service);
        editor.putString(NUM_ORDRE  , numOrdre);
        editor.putString(ETABLISSEMENT_PHONE, phone);
        editor.putString(ETABLISSEMENT_TIME, time);
        editor.putString(ETABLISSEMENT_ADRESSE, place);
        editor.putString(ETABLISSEMENT_WILAYA, wilaya);
        editor.putString(ETABLISSEMENT_COMMUNE, commune);
        editor.putBoolean(ETABLISSEMENT_EXIST, isExist);

        editor.apply();
    }

    public static void saveDoctorInfo(final Context context) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            rootRef.child("Doctors").child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean mContinue = dataSnapshot.child("Name").exists()
                            && dataSnapshot.child("Place").exists()
                            && dataSnapshot.child("Wilaya").exists()

                            && dataSnapshot.child("Commune").exists()
                            && dataSnapshot.child("Phone").exists()
                            && dataSnapshot.child("Speciality").exists()

                            && dataSnapshot.child("Type").exists()
                            && dataSnapshot.child("Time").exists()
                            && dataSnapshot.child("Service").exists()
                            && dataSnapshot.child("NumOrdre").exists()

                            && dataSnapshot.child("ImageUrl").exists()
                            && dataSnapshot.child("DoctorExist").exists();

                    Log.d("logingtest","mContinue doctor = "+ mContinue);

                    Log.d("logingtest","_ID_Firebase = "+ dataSnapshot.child("_ID_Firebase").exists());
                    Log.d("logingtest","Name = "+  dataSnapshot.child("Name").exists());
                    Log.d("logingtest","Place = "+ dataSnapshot.child("Place").exists());
                    Log.d("logingtest","Phone = "+ dataSnapshot.child("Phone").exists());
                    Log.d("logingtest","Wilaya = "+ dataSnapshot.child("Wilaya").exists());

                    Log.d("logingtest","Speciality = "+ dataSnapshot.child("Speciality").exists());


                    Log.d("logingtest","Commune = "+ dataSnapshot.child("Commune").exists());
                    Log.d("logingtest","Type = "+ dataSnapshot.child("Type").exists());
                    Log.d("logingtest","Service = "+ dataSnapshot.child("Service").exists());

                    Log.d("logingtest","NumOrdre = "+ dataSnapshot.child("NumOrdre").exists());
                    Log.d("logingtest","Time = "+ dataSnapshot.child("Time").exists());
                    Log.d("logingtest","ImageUrl = "+ dataSnapshot.child("ImageUrl").exists());
                    Log.d("logingtest","DoctorExist = "+ dataSnapshot.child("DoctorExist").exists());

                    if (mContinue) {
                        String Name = dataSnapshot.child("Name").getValue(String.class);
                        String ImageUrl = dataSnapshot.child("ImageUrl").getValue(String.class);
                        String Type = dataSnapshot.child("Type").getValue(String.class);

                        String service = dataSnapshot.child("Service").getValue(String.class);
                        String numOrdre = dataSnapshot.child("NumOrdre").getValue(String.class);

                        String speciality = dataSnapshot.child("Speciality").getValue(String.class);
                        String time = dataSnapshot.child("Time").getValue(String.class);
                        String phone = dataSnapshot.child("Phone").getValue(String.class);

                        String adress = dataSnapshot.child("Place").getValue(String.class);
                        String willaya = dataSnapshot.child("Wilaya").getValue(String.class);
                        String commune = dataSnapshot.child("Commune").getValue(String.class);
                        boolean isExist = dataSnapshot.child("DoctorExist").getValue(boolean.class);


                        Log.d("logingtest","doctor exist");
                        saveDoctorData(context, Name, Type,speciality,adress,willaya,commune,time,phone,service,numOrdre,isExist, ImageUrl);
                        saveViewPagerInscriptionPosition(context, 1);
                    } else {
                        Log.d("logingtest","profile does not exist");
                        saveDoctorData(context, "", "0","","","0","0","","","","",false, "R.drawable.profile");
                        saveViewPagerInscriptionPosition(context, 1);
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Reminder")
                                .setMessage("vous ne completez pas votre Établissement profile!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, AddUtilisateurActivity.class));
                                    }
                                })
                                .setNegativeButton("Later", null)
                                .setIcon(android.R.drawable.stat_notify_error)
                                .show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "error : " + databaseError.getMessage());
                }
            });
        } else {
            saveDoctorData(context, "", "0","","","0","0","","","","",false, "R.drawable.profile");
            saveViewPagerInscriptionPosition(context, 1);

        }
    }

    private static void saveDoctorData(Context context, String name, String type, String speciality,
                                       String adress, String willaya, String commune, String time,
                                       String phone, String service, String numOrdre, boolean isExist, String imageUrl) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DOCTOR_NAME, name );
        editor.putString( DOCTOR_IMAGE, imageUrl);
        editor.putString(DOCTOR_TYPE, type);

        editor.putString(DOCTOR_SPECIALITY, speciality);
        editor.putString(DOCTOR_SERVICE, service);
        editor.putString(NUM_ORDRE, numOrdre);
        editor.putString(DOCTOR_PHONE, phone);
        editor.putString(DOCTOR_TIME, time);
        editor.putString(DOCTOR_ADRESSE, adress);
        editor.putString(DOCTOR_WILAYA, willaya);
        editor.putString(DOCTOR_COMMUNE, commune);
        editor.putBoolean(DOCTOR_EXIST, isExist);

        editor.apply();
    }




    public static void savePharmaInfo(final Context context) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            rootRef.child("pharmacies").child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    boolean mContinue = ds.child("_ID_Firebase").exists()
                            && ds.child("ImageUrl").exists()
                            && ds.child("Name").exists()
                            && ds.child("Place").exists()
                            && ds.child("Wilaya").exists()
                            && ds.child("Commune").exists()
                            && ds.child("Phone").exists()
                            && ds.child("Time").exists()
                            && ds.child("Description").exists()
                            && ds.child("NumOrdre").exists()
                            && ds.child("Time").exists();

                    Log.d("logingtest","mContinue pharma = "+ mContinue);

                    Log.d("logingtest","_ID_Firebase = "+ ds.child("_ID_Firebase").exists());
                    Log.d("logingtest","Name = "+  ds.child("Name").exists());
                    Log.d("logingtest","Place = "+ ds.child("Place").exists());
                    Log.d("logingtest","Phone = "+ ds.child("Phone").exists());
                    Log.d("logingtest","Wilaya = "+ ds.child("Wilaya").exists());


                    Log.d("logingtest","Commune = "+ ds.child("Commune").exists());
                    Log.d("logingtest","Description = "+ ds.child("Description").exists());

                    Log.d("logingtest","NumOrdre = "+ ds.child("NumOrdre").exists());
                    Log.d("logingtest","Time = "+ ds.child("Time").exists());
                    Log.d("logingtest","ImageUrl = "+ ds.child("ImageUrl").exists());
               //     Log.d("logingtest","DoctorExist = "+ dataSnapshot.child("DoctorExist").exists());

                    if (mContinue) {
                        String Name = ds.child("Name").getValue(String.class);
                        String Place = ds.child("Place").getValue(String.class);
                        String Phone = ds.child("Phone").getValue(String.class);
                        String time = ds.child("Time").getValue(String.class);
                        String wilaya= ds.child("Wilaya").getValue(String.class);
                        String commune= ds.child("Commune").getValue(String.class);
                        String description = ds.child("Description").getValue(String.class);
                        String ImageUrl = ds.child("ImageUrl").getValue(String.class);
                        String numOrdre = ds.child("NumOrdre").getValue(String.class);
                        boolean isExist = ds.child("PharmacieExist").getValue(boolean.class);


                        Log.d("logingtest","doctor exist");
                        savePharmaData(context, Name, Place, wilaya,commune,time,Phone,description,numOrdre,isExist, ImageUrl);
                        saveViewPagerInscriptionPosition(context, 2);
                    } else {
                        Log.d("logingtest","profile does not exist");
                        savePharmaData(context, "", "", "0","0","","","","",false, "R.drawable.profile");
                        saveViewPagerInscriptionPosition(context, 2);
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Reminder")
                                .setMessage("vous ne completez pas votre Établissement profile!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, AddUtilisateurActivity.class));
                                    }
                                })
                                .setNegativeButton("Later", null)
                                .setIcon(android.R.drawable.stat_notify_error)
                                .show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "error : " + databaseError.getMessage());
                }
            });
        } else {
            savePharmaData(context, "", "", "0","0","","","","",false, "R.drawable.profile");
            saveViewPagerInscriptionPosition(context, 2);

        }
    }

    private static void savePharmaData(Context context, String name, String place,
                                       String wilaya, String commune, String time,
                                       String phone, String description, String numOrdre,
                                       boolean isExist, String imageUrl) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PHARMA_NAME, name );
        editor.putString(PHARMA_IMAGE, imageUrl);
        editor.putString(PHARMA_DESCRIPTION, description);

        editor.putString(PHARMA_TIME, time);
        editor.putString(NUM_ORDRE, numOrdre);
        editor.putString(PHARMA_PHONE, phone);
        editor.putString(PHARMA_ADRESSE, place);
        editor.putString(PHARMA_WILAYA, wilaya);
        editor.putString(PHARMA_COMMUNE, commune);
        editor.putBoolean(PHARMA_EXIST, isExist);

        editor.apply();
    }




    public static void initialSpecDB(Context context){
        final DBManagerSpeciality db = new DBManagerSpeciality(context);
        db.open();
        final Integer[] count = {0};
           String[] array = context.getResources().getStringArray(R.array.speciality);
           specRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for (final DataSnapshot ds : dataSnapshot.getChildren()){
                       if (db.CheckIsDataAlreadyInDBorNot(ds.getKey())){
                           new AsyncTask<Void, Void, Void>() {
                               @Override
                               protected Void doInBackground(Void... voids) {
                                   db.update(count[0],ds.getKey(),ds.getValue(String.class));
                                   return null;
                               }
                           };
                       }else {
                           new AsyncTask<Void, Void, Void>() {
                               @Override
                               protected Void doInBackground(Void... voids) {
                                   db.insert(count[0],ds.getKey(),ds.getValue(String.class));
                                   return null;
                               }
                           };
                       }
                       count[0]++;
                   }
                   db.close();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Log.d("specialityTest","error "+ databaseError.getMessage());
                   db.close();
               }
           });
    }


    public static void saveData(Context context, String userName,String userType, String email, String age, String phone, String adress, String willaya, String commune,boolean isDonor,String grp,String userImage, boolean isLogin, boolean isProfileExist) {
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
    }

    public static boolean firstTime(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (Pref.getBoolean(FIRST_TIME, true)) {
            SharedPreferences.Editor editor = Pref.edit();
            editor.putBoolean(FIRST_TIME, false);
            editor.apply();
            return true;
        } else return false;
    }



    public static String getType(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getString(KEY_USER_TYPE, "0");

    }



/*
    public static void getNbrMessageNoRead(final Context context) {
        final DBManagerMessage db;
        db = new DBManagerMessage(context);
        final String[] messageSender ={""};
        final String[] messageShort = {""};
        final String[] messageFirebaseId = {""};
        final String[] messageImage = { "" };

        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = Pref.edit();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    db.open();
                    db.deleteAll();
                    count = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists() && ds.child("ID_Reciver").exists() && ds.child("Is_Readed").exists() && ds.child("Date").exists() && ds.child("AllMsg").exists();
                        if (is_exist) {
                            String SenderName = ds.child("Sender_Name").getValue(String.class);
                            String SenderImage;
                            if (ds.child("Sender_Image").exists()) {
                                SenderImage = ds.child("Sender_Image").getValue(String.class);
                            } else SenderImage = "R.drawable.profile";
                            String message = ds.child("message_envoyer").getValue(String.class);
                            String fullMsg = ds.child("AllMsg").getValue(String.class);
                            String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                            String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                            if (Is_Readed.equals("false") && !ID_firebase.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                messageSender[0] = SenderName;
                                messageShort[0] = message;
                                messageFirebaseId[0] = ID_firebase;
                                messageImage[0] = SenderImage;

                                count += 1;
                            }
                            String Date = ds.child("Date").getValue(String.class);
                            if (!ID_firebase.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (db.CheckIsDataAlreadyInDBorNot(ID_firebase)) {
                                    db.update(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                } else {
                                    db.insert(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                }
                            }
                        }
                    }
                    String oldCount = getNbrMsgs(context);
                    if (!oldCount.equals(String.valueOf(count))) {
                        if (Integer.parseInt(oldCount) < count && !messageFirebaseId[0].equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            showMessageNotification(context, messageSender[0], messageShort[0], messageFirebaseId[0], messageImage[0]);
                        }
                        editor.putString(KEY_NUMBER_MESSAGES_NON_READ, String.valueOf(count));
                        editor.apply();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "erorr : " + databaseError.getMessage());
                }
            });
        } else {

            editor.putString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
            editor.apply();
        }
    }
*/

/*
    private static void showMessageNotification(final Context context,
                                                final String messageSender, final String message, String fireBaseId,String image) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.logo);

        Intent messageActivityIntent = new Intent(context, chatRoom.class);
        // noteActivityIntent.putExtra(NoteActivity.NOTE_ID, noteId);
        messageActivityIntent.putExtra(RECIVER, fireBaseId);
        messageActivityIntent.putExtra(RECIVER_IMAGE, image);
        messageActivityIntent.putExtra(SENDER, messageSender);





        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
        // Set appropriate defaults for the notification light, sound,
        // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(messageSender)
                .setContentText(message)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                // Set ticker text (preview) information for this notification.
                .setTicker("Nauveau message")

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(messageSender)
                        .setSummaryText("Neauveau message"))

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                messageActivityIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))

                .addAction(
                        0,
                        "Voire tous les messages",
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, messageBoit.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);




        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel= new NotificationChannel("1001","NOTIFICATION_CHANNEL_NAME",importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            //  notificationChannel.setVibrationPattern(new Long[]{Long.valueOf(100), Long.valueOf(200), Long.valueOf(300), Long.valueOf(400), Long.valueOf(500), Long.valueOf(400), Long.valueOf(300), Long.valueOf(200), Long.valueOf(400)});
            builder.setChannelId("1001");
            assert notificationManagerCompat != null;
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        assert notificationManagerCompat != null;

        notificationManagerCompat.notify((int)System.currentTimeMillis(),builder.build());
    }

*/

    public static String getNbrMsgs(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
    }

    public static void getNbrMessageNoRead(final Context context) {
        final DBManagerMessage db;
        db = new DBManagerMessage(context);
        final String[] messageSender ={""};
        final String[] messageShort = {""};
        final String[] messageFirebaseId = {""};
        final String[] messageImage = { "" };

        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = Pref.edit();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    db.open();
                    db.deleteAll();
                    count = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists() && ds.child("ID_Reciver").exists() && ds.child("Is_Readed").exists() && ds.child("Date").exists() && ds.child("AllMsg").exists();
                        if (is_exist) {
                            String SenderName = ds.child("Sender_Name").getValue(String.class);
                            String SenderImage;
                            if (ds.child("Sender_Image").exists()) {
                                SenderImage = ds.child("Sender_Image").getValue(String.class);
                            } else SenderImage = "R.drawable.profile";
                            String message = ds.child("message_envoyer").getValue(String.class);
                            String fullMsg = ds.child("AllMsg").getValue(String.class);
                            String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                            String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                            if (Is_Readed.equals("false") && !ID_firebase.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                messageSender[0] = SenderName;
                                messageShort[0] = message;
                                messageFirebaseId[0] = ID_firebase;
                                messageImage[0] = SenderImage;

                                count += 1;
                            }
                            String Date = ds.child("Date").getValue(String.class);
                            if (!ID_firebase.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (db.CheckIsDataAlreadyInDBorNot(ID_firebase)) {
                                    db.update(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                } else {
                                    db.insert(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                }
                            }
                        }
                    }
                    String oldCount = getNbrMsgs(context);
                    if (!oldCount.equals(String.valueOf(count))) {
                        if (Integer.parseInt(oldCount) < count && !messageFirebaseId[0].equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            showMessageNotification(context, messageSender[0], messageShort[0], messageFirebaseId[0], messageImage[0]);
                        }
                        editor.putString(KEY_NUMBER_MESSAGES_NON_READ, String.valueOf(count));
                        editor.apply();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "erorr : " + databaseError.getMessage());
                }
            });
        } else {

            editor.putString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
            editor.apply();
        }
    }
    private static void showMessageNotification(final Context context,
                                                final String messageSender, final String message, String fireBaseId,String image) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.logo);

        Intent messageActivityIntent = new Intent(context, chatRoom.class);
        // noteActivityIntent.putExtra(NoteActivity.NOTE_ID, noteId);
        messageActivityIntent.putExtra(RECIVER, fireBaseId);
        messageActivityIntent.putExtra(RECIVER_IMAGE, image);
        messageActivityIntent.putExtra(SENDER, messageSender);





        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentTitle(messageSender)
                .setContentText(message)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                // Set ticker text (preview) information for this notification.
                .setTicker("Nauveau message")

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(messageSender)
                        .setSummaryText("Neauveau message"))

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                messageActivityIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))

                .addAction(
                        0,
                        "Voire tous les messages",
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, messageBoit.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);




        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel= new NotificationChannel("1001","NOTIFICATION_CHANNEL_NAME",importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            //  notificationChannel.setVibrationPattern(new Long[]{Long.valueOf(100), Long.valueOf(200), Long.valueOf(300), Long.valueOf(400), Long.valueOf(500), Long.valueOf(400), Long.valueOf(300), Long.valueOf(200), Long.valueOf(400)});
            builder.setChannelId("1001");
            assert notificationManagerCompat != null;
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        assert notificationManagerCompat != null;

        notificationManagerCompat.notify((int)System.currentTimeMillis(),builder.build());
    }


}
