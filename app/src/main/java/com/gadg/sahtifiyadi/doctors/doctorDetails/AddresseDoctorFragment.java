package com.gadg.sahtifiyadi.doctors.doctorDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerDoctor;
import com.gadg.sahtifiyadi.items.Doctors;
import com.gadg.sahtifiyadi.login.LoginActivity;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.lang3.text.WordUtils;

public class AddresseDoctorFragment extends Fragment {

    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";


    public static final String ADRESS = "ADRESS";
    public ImageButton bAdrres, bPhone, bMessage;
    public TextView tText;
    private Doctors mCurrentDoctor;

    public AddresseDoctorFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addresse_doctor, container, false);
        bAdrres= (ImageButton) view.findViewById(R.id.fragment_adress_doctor_adrress_image_button);
       tText= (TextView) view.findViewById(R.id.fragment_adress_doctor_adrress_text);

        bPhone = (ImageButton) view.findViewById(R.id.details_doctor_adress_phone);
        bMessage = (ImageButton) view.findViewById(R.id.details_adress_doctor_messsage);
        view.findViewById(R.id.fragment_adress_doctor_first_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+ Uri.encode(mCurrentDoctor.getPlaceDoctor().trim())));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });

//
        bPhone.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fall_down_retard));
        bMessage.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fall_down_retard));

        //initialiser les views
        initialeTheViewIntent();
        //onClick
        bAdrres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        //
        bPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //téléphoner
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mCurrentDoctor.getPhone().replaceAll(" ","")));
                startActivity(i);
            }
        });
        bMessage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //aller aux chat message
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent i = new Intent(getContext(), chatRoom.class);
                    i.putExtra(RECIVER, mCurrentDoctor.getDoctor_ID_Firebase());
                    i.putExtra(RECIVER_IMAGE, mCurrentDoctor.getImageUrl());
                    i.putExtra(SENDER, mCurrentDoctor.getNameDoctor());
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.open_enter, R.anim.nav_default_exit_anim);
                }else {
                    //
                    new AlertDialog.Builder(getContext())
                            .setTitle("Service non disponible")
                            .setMessage("Vous devez vous connecter d'abord!")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setIcon(R.drawable.ic_baseline_not_interested_red)
                            .show();
                }
            }
        });


        return view;
    }
    private void initialeTheViewIntent () {
        DBManagerDoctor db = new DBManagerDoctor(getContext());
        db.open();

        Intent i = getActivity().getIntent();
        int id =  i.getIntExtra("id",1);

        mCurrentDoctor = db.getDoctorFromID(id);

        tText.setText(WordUtils.capitalizeFully(mCurrentDoctor.getPlaceDoctor()));
        db.close();

    }
}
