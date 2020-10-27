package com.gadg.sahtifiyadi.login.profiles;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.ui.don_de_sang.don_de_song;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.DOCTOR_COMMUNE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.KEY_IS_USER_DONOR;
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
import static com.gadg.sahtifiyadi.utilities.tools.getCommuns;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link updateNormalUserFragment#} factory method to
 * create an instance of this fragment.
 */
public class updateNormalUserFragment extends Fragment {



    private ImageView userImage;

    private ImageButton b1,b2;
    private EditText ageEdite;
    private Integer age = 20;
    private Button next;

    private CheckBox checkBox;

    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence> commmunsCodeAdapter;

    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private int[] mType ={0};

    private boolean isDonor=false;

    private EditText adreassEdit, nameEdit;

    private Uri mImageUri;
    public static int PICK_IMAGE = 1;


    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String mEmail;
    private String mPhone;
    private String url;
    private FirebaseUser userFireBase;

    private RadioGroup mRadioGroup_1;
    private RadioGroup mRadioGroup_2;

    private String[] mBlood={"O","+"};


    public updateNormalUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_normal_user, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("Users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userImage = (ImageView) view.findViewById(R.id.add_normal_user_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });


        ageEdite = (EditText) view.findViewById(R.id.add_age);

        b1 = (ImageButton) view.findViewById(R.id.minis_age);
        b2 = (ImageButton) view.findViewById(R.id.plus_age);

        adreassEdit = (EditText) view.findViewById(R.id.add_location);
        nameEdit = (EditText) view.findViewById(R.id.add_nom);

        spinnerWilaya = view.findViewById(R.id.add_spinner_wilaya);
        spinnerCommuns = view.findViewById(R.id.add_spinner_communs);


        checkBox = (CheckBox) view.findViewById(R.id.add_donnateur);

        next = (Button) view.findViewById(R.id.update_user);
        mProgressBar = view.findViewById(R.id.progress_b);
        userFireBase = FirebaseAuth.getInstance().getInstance().getCurrentUser();
        mRadioGroup_1 = (RadioGroup) view.findViewById(R.id.blooddonor_fragment_radiobottongroup);
        mRadioGroup_2 = (RadioGroup) view.findViewById(R.id.blooddonor_fragment_radiobottongroup_second);



        mRadioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blooddonor_o) {
                    mBlood[0] = "O";
                } else if (checkedId == R.id.blooddonor_a) {
                    mBlood[0] = "A";
                } else if (checkedId == R.id.blooddonor_b) {
                    mBlood[0] = "B";
                } else if (checkedId == R.id.blooddonor_ab) {
                    mBlood[0] = "AB";
                }
            }
        });
        mRadioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blooddonor_plus) {
                    mBlood[1] = "+";
                } else if (checkedId == R.id.blooddonor_minus) {
                    mBlood[1] = "-";
                }
            }
        });


        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                mAdress[2] = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, getCommuns(position));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                //
              initialiseView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() - 1;
                ageEdite.setText(age.toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() + 1;
                ageEdite.setText(age.toString());
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFireBase(url);
            }
        });


        return view;
    }

    private void initialiseView() {
        SharedPreferences Pref = getContext().getSharedPreferences(PREFERENCE_NAME, getContext().MODE_PRIVATE);
        int wilaya = Integer.parseInt(Pref.getString(KEY_USER_WILAYA, "0"));
        try {
            spinnerWilaya.setSelection(wilaya);
        }catch (Exception e){
            Log.d("testAkramspinner",e.getMessage());
        }
        ageEdite.setText(Pref.getString(KEY_USER_AGE, "20"));
        nameEdit.setText(Pref.getString(KEY_USER_NAME, ""));
        adreassEdit.setText(Pref.getString(KEY_USER_ADRESSES, ""));
        url = Pref.getString(KEY_USER_IMAGE, "R.drawable.profile");
        Glide.with(getContext()).load(url).into(userImage);
        isDonor = Pref.getBoolean(KEY_IS_USER_DONOR,false);
        checkBox.setChecked(isDonor);
        mEmail = Pref.getString(KEY_USER_EMAIL, "");
        mPhone = Pref.getString(KEY_USER_PHONE, "");
        mType[0] = Integer.parseInt(Pref.getString(KEY_USER_TYPE, "0"));
        String grp = Pref.getString(KEY_USER_GRP, "+O");

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDonor = isChecked;
            }
        });

        int commune = Integer.parseInt(Pref.getString(KEY_USER_COMMUNE, "0"));
        try {
            spinnerCommuns.setSelection(commune);

        }catch (Exception e){
            Log.d("testAkramspinner",e.getMessage());
        }
    }

    private void saveImageInFireBise() {

        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    url = uri.toString();
                                 //   updateFireBase(url);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateFireBase(String  image_url) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Mise à jour");
        progressDialog.setMessage("S'il veux plait attendez un peu de moment ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
        progressDialog.setIndeterminate(true);

        progressDialog.show();
         String name = nameEdit.getText().toString().trim() ;
         String email = mEmail;
         String adress = adreassEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
         String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
         String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());

         final String type = String.valueOf(mType[0]);
         String phone = mPhone;
         String lage = String.valueOf(age);
         final String grp = mBlood[0]+mBlood[1];


/*
        Log.d("AKRATMTESTTEST","name is "+name);
        Log.d("AKRATMTESTTEST","adress is "+adress);
        Log.d("AKRATMTESTTEST","willaya is "+willaya);
        Log.d("AKRATMTESTTEST","commune is "+commune);
        Log.d("AKRATMTESTTEST","phone is "+phone);
        Log.d("AKRATMTESTTEST","type is "+type);
        Log.d("AKRATMTESTTEST","age is "+lage);
        Log.d("AKRATMTESTTEST","user is "+user.getUid());


 */

        Map<String, Object> UserData = new HashMap<String, Object>();

        UserData.put("UserName", name);
        UserData.put("UserGrp", grp);
        UserData.put("UserEmail", email);
        UserData.put("UserType", type);
        UserData.put("_ID_Firebase", userFireBase.getUid());
        UserData.put("UserAge", lage);
        UserData.put("UserPhone", phone);
        UserData.put("UserAdress", adress);
        UserData.put("UserWillaya", willaya);
        UserData.put("UserCommune", commune);
        UserData.put("UserDonnation", isDonor);
        UserData.put("UserImageUrl", image_url);
        UserData.put("UserExist", true);


        mDatabaseRef.child(userFireBase.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"vos information ont été mise à jour", Toast.LENGTH_LONG).show();
                    if (isDonor){
                        updateDonDeSang();
                    }
                } else {
                    String error;
                    error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        });
    }

    private void updateDonDeSang() {

        final String name = nameEdit.getText().toString().trim() ;
        final String adress = adreassEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
        final String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
        final String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());
        final String grp = mBlood[0]+mBlood[1];
        final String phone = mPhone;
        final String lage = String.valueOf(age);
        DatabaseReference mdonDeSangRef = FirebaseDatabase.getInstance().getReference("Donateurs");

        String donateurID = FirebaseAuth.getInstance().getInstance().getCurrentUser().getUid();
        don_de_song donateur=new don_de_song (donateurID,name,adress,willaya,commune,phone ,
                lage,grp,url);
        mdonDeSangRef.child(donateurID).setValue(donateur);

    }

    public void onClicks() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            saveImageInFireBise();
            try {
                AppCompatActivity test=new AppCompatActivity();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), mImageUri);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        AppCompatActivity test=new AppCompatActivity();
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
