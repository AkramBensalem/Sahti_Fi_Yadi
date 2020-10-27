package com.gadg.sahtifiyadi.login.addUtilisateur;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.gadg.sahtifiyadi.MainActivity;
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
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveViewPagerInscriptionPosition;
import static com.gadg.sahtifiyadi.utilities.tools.getCommuns;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addNormalUserFragment#} factory method to
 * create an instance of this fragment.
 */
public class addNormalUserFragment extends Fragment {



    private ImageView userImage;

    private ImageButton b1,b2;
    private TextView ageq, mNameText;
    private Integer age = 20;
    private Button next;

    private RadioGroup mRadioGroup_1;
    private RadioGroup mRadioGroup_2;

    private CheckBox checkBox;

    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence> commmunsCodeAdapter;

    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private int[] mType ={0};

    private boolean isDonor=false;

    private EditText adreassEdit;

    private Uri mImageUri;
    public static int PICK_IMAGE = 1;


    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String mName;
    private String mEmail;
    private String mPhone;


    private RadioGroup mRadioGroup_3;
    private RadioGroup mRadioGroup_4;

    private String[] mBlood={"O","+"};
    private FirebaseUser user;


    public addNormalUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_normal_user, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("Users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userImage = (ImageView) view.findViewById(R.id.add_normal_user_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });


        ageq = (TextView) view.findViewById(R.id.add_age);
        mNameText= (TextView) view.findViewById(R.id.add_normal_user_name);

        b1 = (ImageButton) view.findViewById(R.id.minis_age);
        b2 = (ImageButton) view.findViewById(R.id.plus_age);

        adreassEdit = (EditText) view.findViewById(R.id.add_location);

        spinnerWilaya = view.findViewById(R.id.add_spinner_wilaya);
        spinnerCommuns = view.findViewById(R.id.add_spinner_communs);

        mRadioGroup_1 = (RadioGroup) view.findViewById(R.id.add_fragment_radiobottongroup);
        mRadioGroup_2 = (RadioGroup) view.findViewById(R.id.add_fragment_radiobottongroup_second);

        checkBox = (CheckBox) view.findViewById(R.id.add_donnateur);

        next = (Button) view.findViewById(R.id.add_next);
        mProgressBar = view.findViewById(R.id.progress_b);

        initialiseView();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDonor = isChecked;
            }
        });

        mRadioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.add_normal_user) {
                    mRadioGroup_2.clearCheck();
                    mType[0] = 0;
                } else if (checkedId == R.id.add_medcin) {
                    mRadioGroup_2.clearCheck();
                    mType[0] = 1;
                } else if (checkedId == R.id.add_pharmacie) {
                    mRadioGroup_2.clearCheck();
                    mType[0] = 2;
                }
            }
        });
        mRadioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.add_hopital) {
                    mRadioGroup_1.clearCheck();
                    mType[0] = 3;
                } else if (checkedId == R.id.add_labo) {
                    mRadioGroup_1.clearCheck();
                    mType[0] = 4;
                }

            }
        });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() - 1;
                ageq.setText(age.toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() + 1;
                ageq.setText(age.toString());
            }
        });



        mRadioGroup_3 = (RadioGroup) view.findViewById(R.id.blooddonor_fragment_radiobottongroup);
        mRadioGroup_4 = (RadioGroup) view.findViewById(R.id.blooddonor_fragment_radiobottongroup_second);



        mRadioGroup_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        mRadioGroup_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoInFireBsae();
            }
        });


        return view;
    }

    private void initialiseView() {
        Intent i = getActivity().getIntent();
        mName = i.getStringExtra("NAME");
        mEmail = i.getStringExtra("EMAIL");
        mPhone = i.getStringExtra("PHONE");

        mNameText.setText("Salut "+mName);
    }

    private void saveUserInfoInFireBsae() {

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
                                    String url =uri.toString();
                                    updateFireBase(url);

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
            Toast.makeText(getContext(), "Aucun image été sélectionné", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateFireBase(final String  image_url) {

         String name = mName;
         String email = mEmail;
        String adress = adreassEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
        String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
         String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());
         final String type = String.valueOf(mType[0]);
         String phone = mPhone;
         String lage = String.valueOf(age);
        final String grp = mBlood[0]+mBlood[1];

        user = FirebaseAuth.getInstance().getCurrentUser();


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
        UserData.put("UserEmail", email);
        UserData.put("UserGrp", grp);
        UserData.put("UserType", type);
        UserData.put("_ID_Firebase", user.getUid());
        UserData.put("UserAge", lage);
        UserData.put("UserPhone", phone);
        UserData.put("UserAdress", adress);
        UserData.put("UserWillaya", willaya);
        UserData.put("UserCommune", commune);
        UserData.put("UserDonnation", isDonor);
        UserData.put("UserImageUrl", image_url);
        UserData.put("UserExist", true);


        mDatabaseRef.child(user.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (isDonor){
                    updateDonDeSang(image_url);
                }
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Vos données sont été mise a jours", Toast.LENGTH_SHORT).show();
                    saveViewPagerInscriptionPosition(getContext(),Integer.parseInt(type));
                    if (type != "0") {
                        ViewPager tabHost = (ViewPager) getActivity().findViewById(R.id.insetion_viewpager);
                        tabHost.setCurrentItem(Integer.parseInt(type));
                    }else {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);;
                    }
                } else {
                    String error;
                    error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

                }
            }
        });
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

    private void updateDonDeSang(String url) {

        String name = mName;
        String adress = adreassEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
        String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
        String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());
        String phone = mPhone;
        String lage = String.valueOf(age);
        final String grp = mBlood[0]+mBlood[1];

        DatabaseReference mdonDeSangRef = FirebaseDatabase.getInstance().getReference("Donateurs");

        String donateurID = FirebaseAuth.getInstance().getInstance().getCurrentUser().getUid();
        don_de_song donateur=new don_de_song (donateurID,name,adress,willaya,commune,phone ,
                lage,grp,url);
        mdonDeSangRef.child(donateurID).setValue(donateur);

    }


}
