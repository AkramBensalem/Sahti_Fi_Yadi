package com.gadg.sahtifiyadi.Etablissement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerEtablissement;
import com.gadg.sahtifiyadi.items.Etablissement;
import com.gadg.sahtifiyadi.items.pharmacy;
import com.gadg.sahtifiyadi.login.LoginActivity;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_HOSPITAL;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_LABORATOIR;
import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.RECIVER;
import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.RECIVER_IMAGE;
import static com.gadg.sahtifiyadi.doctors.doctorDetails.PersonalInformationDoctorFragment.SENDER;

public class EtablissementProfileActivity extends AppCompatActivity {
    public static final String ID = "id";
    public static final String TYPE = "type";
    private TextView desView, phoneView, serviceView, locationView;
    private ImageView imageView;
    private String mTableName;
    private Etablissement currentEtablissement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablissement_profile);



        imageView = findViewById(R.id.activity_etablisment_image);
        phoneView = findViewById(R.id.activity_etablisment_contact);
        serviceView = findViewById(R.id.activity_etablisment_service);
        desView = findViewById(R.id.activity_etablisment_description);
        locationView = findViewById(R.id.activity_etablisment_location);



        initialView();
        findViewById(R.id.etablissement_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //téléphoner
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+currentEtablissement.getHopitalContact().replaceAll(" ","")));
                startActivity(i);
            }
        });
        // animate
        desView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        serviceView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        phoneView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        locationView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));

    }

    private void initialView() {

        Intent i = getIntent();
        mTableName = i.getIntExtra(TYPE, 1) == 0 ? TABLE_NAME_HOSPITAL : TABLE_NAME_LABORATOIR;
        DBManagerEtablissement db = new DBManagerEtablissement(getBaseContext(), mTableName);
        db.open(mTableName);
        currentEtablissement = db.getHopitalFromID(i.getIntExtra(ID, 1));
        db.close();
        if (currentEtablissement != null) {
            String imageUrl = currentEtablissement.getImageResource();
            if (imageUrl.equals("R.drawable.profile")) {
                Glide.with(this).load(R.drawable.profile).into(imageView);
            } else {
                Glide.with(this).load(imageUrl).into(imageView);
            }
            phoneView.setText(currentEtablissement.getHopitalContact());
            serviceView.setText(currentEtablissement.getHopitalservice());
            desView.setText(currentEtablissement.getHopitaldescription());
            locationView.setText(currentEtablissement.getHopitalPlace());
            this.setTitle(currentEtablissement.getHopitalName());
        }
    }

    public void GoToMap(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+ Uri.encode(currentEtablissement.getHopitalPlace().trim())));
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }

    public void goToChatRoom(View view) {
        //aller aux chat message
     //   Log.d("akramtesting","9bal ma yadkhol yodkhol + id : "+FirebaseAuth.getInstance().getCurrentUser().getUid());
       if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("akramtesting","9bal ma yadkhol LOLA");

            Intent i = new Intent(getBaseContext(), chatRoom.class);
            i.putExtra(RECIVER, currentEtablissement.getHospital_ID_Firebase());
            i.putExtra(RECIVER_IMAGE, currentEtablissement.getImageResource());
            i.putExtra(SENDER, currentEtablissement.getHopitalName());
            startActivity(i);
            overridePendingTransition(R.anim.open_enter, R.anim.nav_default_exit_anim);
        }else {
            //
            Log.d("akramtesting","yodkhol");
            new AlertDialog.Builder(EtablissementProfileActivity.this)
                    .setTitle("Service non disponible")
                    .setMessage("Vous devez vous connecter d'abord!")
                    .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(EtablissementProfileActivity.this, LoginActivity.class));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setIcon(R.drawable.ic_baseline_not_interested_red)
                    .show();


        }


    }
}
