package com.gadg.sahtifiyadi.login.profiles;

import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;
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
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.NUM_ORDRE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_ADRESSE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_COMMUNE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_DESCRIPTION;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_IMAGE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_NAME;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_PHONE;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_TIME;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PHARMA_WILAYA;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.PREFERENCE_NAME;
import static com.gadg.sahtifiyadi.utilities.tools.getCommuns;


public class updatePharmacieFragment extends Fragment {
    private ImageView userImage;

    private Button next;

    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence> commmunsCodeAdapter;

    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private EditText nomEdit,phonEdit, descriptionEdit,timeEdit, ordreEdit, adrressEdit;

    private Uri mImageUri;
    public static int PICK_IMAGE = 1;


    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String mPhone;

    public updatePharmacieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_pharmacie, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("pharmacies");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pharmacies");

        userImage = (ImageView) view.findViewById(R.id.add_pharmacie_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });


        nomEdit = (EditText) view.findViewById(R.id.pharamcie_nom);
        timeEdit = (EditText) view.findViewById(R.id.pharmacie_ouverture);
        ordreEdit =(EditText) view.findViewById(R.id.pharamcie_dorde);
        phonEdit =(EditText) view.findViewById(R.id.pharmacie_phone);
        descriptionEdit =(EditText) view.findViewById(R.id.pharmacie_description);
        adrressEdit = (EditText) view.findViewById(R.id.pharmacie_location);

        spinnerWilaya = view.findViewById(R.id.pharmacie_spinner_wilaya);
        spinnerCommuns = view.findViewById(R.id.pharmacie_spinner_communs);

        next = (Button) view.findViewById(R.id.add_next_pharmacie);
        mProgressBar = view.findViewById(R.id.pharmacie_progressbar);

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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoInFireBsae();
            }
        });



        return view;
    }


    private void initialiseView() {
        SharedPreferences Pref = getContext().getSharedPreferences(PREFERENCE_NAME, getContext().MODE_PRIVATE);
        int wilaya = Integer.parseInt(Pref.getString(PHARMA_WILAYA, "0"));
        try {
            spinnerWilaya.setSelection(wilaya);
        }catch (Exception e){
            Log.d("testAkramspinner",e.getMessage());
        }
        nomEdit.setText(Pref.getString(PHARMA_NAME, ""));
        ordreEdit.setText(Pref.getString(NUM_ORDRE, "0"));
        adrressEdit.setText(Pref.getString(PHARMA_ADRESSE, ""));
        mImageUri = Uri.parse(Pref.getString(PHARMA_IMAGE, "R.drawable.profile"));
        Glide.with(getContext()).load(mImageUri).into(userImage);
        descriptionEdit.setText(Pref.getString(PHARMA_DESCRIPTION, ""));
        timeEdit.setText(Pref.getString(PHARMA_TIME, "08:00 - 16:00"));


        mPhone = Pref.getString(PHARMA_PHONE, "");
        phonEdit.setText(mPhone);
        int commune = Integer.parseInt(Pref.getString(PHARMA_COMMUNE, "0"));
        try {
            spinnerCommuns.setSelection(commune);

        }catch (Exception e){
            Log.d("testAkramspinner",e.getMessage());
        }

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
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateFireBase(String  image_url) {

        String pharmacieName = nomEdit.getText().toString();
        String adress = adrressEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
        String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
        String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());
        String ouverture = timeEdit.getText().toString();
        String numOrdre = ordreEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        String phone = phonEdit.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();
        Map<String, Object> DoctorUserData = new HashMap<String, Object>();

        DoctorUserData.put("Name", pharmacieName);
        DoctorUserData.put("Place", adress);
        DoctorUserData.put("Wilaya", willaya);
        DoctorUserData.put("Commune", commune);
        DoctorUserData.put("Phone", phone);
        DoctorUserData.put("Time", ouverture);
        DoctorUserData.put("NumOrdre", numOrdre);
        DoctorUserData.put("Description", description);
        DoctorUserData.put("ImageUrl", image_url);
        DoctorUserData.put("_ID_Firebase", user.getUid());
        DoctorUserData.put("PharmacieExist", true);


        mDatabaseRef.child(user.getUid()).setValue(DoctorUserData).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                      Toast.makeText(getContext(),"vos information ont été mise à jour", Toast.LENGTH_LONG).show();
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


}
