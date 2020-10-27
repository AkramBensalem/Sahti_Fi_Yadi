package com.gadg.sahtifiyadi.pharmacies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerPharmacy;
import com.gadg.sahtifiyadi.items.pharmacy;
import com.gadg.sahtifiyadi.login.LoginActivity;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.RECIVER;
import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.RECIVER_IMAGE;
import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.SENDER;

public class pharmacies_info extends AppCompatActivity {
    public GoogleMap map;
    public ImageView image;
    public TextView contact;
    public TextView location;
    public TextView time;
    public TextView description;
    public Intent intent;
    public GradientDrawable mGradientDrawable;
    private String mName,mPhone ,mPlace, mImage, mDescription, mTime;
    private pharmacy mCurrentPharmacy;

    private static final int REQUEST_CALL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies_info);

        //
        description=(TextView) findViewById(R.id.description_ph_info);
        image=(ImageView) findViewById(R.id.image_ph_info);
        contact=(TextView) findViewById(R.id.contact_ph_info);
        location=(TextView) findViewById(R.id.location_ph_info);
        time=(TextView) findViewById(R.id.time_ph_info);


        initializeView();

        findViewById(R.id.pharmay_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_ph_info();
            }
        });
        findViewById(R.id.pharmay_phone_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

      findViewById(R.id.pharmacy_location).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              GoToMap();
          }
      });
    }

    private void initializeView() {
        intent=getIntent();
        DBManagerPharmacy db = new DBManagerPharmacy(this);
        db.open();
        int id =  getIntent().getIntExtra("id",1);
        mCurrentPharmacy = db.getPharmacieFromID(id);
        mImage = mCurrentPharmacy.getImageUrl();
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        if (mImage.equals("R.drawable.profile")){
            Glide.with(this).load(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(image);
        }else {
            Glide.with(this).load(mImage)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(image);
        }
        mName = WordUtils.capitalizeFully(mCurrentPharmacy.getThename());
        this.setTitle(mName);

        mTime = mCurrentPharmacy.getTime();
        mPhone = mCurrentPharmacy.getPhone();
        mPlace = mCurrentPharmacy.getTheadress();
        mDescription = mCurrentPharmacy.getDescription();
        mTime = mCurrentPharmacy.getTime();


        location.setText(mPlace);
        description.setText(mDescription);
        contact.setText(mPhone);

    }

    public void txt_ph_info() {
        //aller aux chat message
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(getBaseContext(), chatRoom.class);
            i.putExtra(RECIVER, mCurrentPharmacy.getPHARMA_ID_Firebase());
            i.putExtra(RECIVER_IMAGE, mCurrentPharmacy.getImageUrl());
            i.putExtra(SENDER, mCurrentPharmacy.getThename());
            startActivity(i);
            overridePendingTransition(R.anim.open_enter, R.anim.nav_default_exit_anim);
        }else {
            //
            new AlertDialog.Builder(pharmacies_info.this)
                    .setTitle("Service non disponible")
                    .setMessage("Vous devez vous connecter d'abord!")
                    .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(pharmacies_info.this, LoginActivity.class));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setIcon(R.drawable.ic_baseline_not_interested_red)
                    .show();
        }

    }
    private void makePhoneCall() {
        //téléphoner
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mCurrentPharmacy.getPhone().replaceAll(" ","")));
        startActivity(i);
    }

    public void GoToMap() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+ Uri.encode(mCurrentPharmacy.getTheadress().trim())));
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }
}
