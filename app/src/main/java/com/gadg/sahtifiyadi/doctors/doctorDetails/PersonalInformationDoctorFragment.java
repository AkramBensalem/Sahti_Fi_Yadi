package com.gadg.sahtifiyadi.doctors.doctorDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class PersonalInformationDoctorFragment extends Fragment {


    //Les constants

    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";

    //Les Views
   public ImageButton bService, bTime, bPhone, bMessage;
    public TextView tSpecialité,tService,tTime;

    public LinearLayout specLinear, serviceLinear, timeLinear, typeLinear;
    private Doctors mCurrentDoctor;

    public PersonalInformationDoctorFragment(){
        //constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_information_doctor, container, false);


        bService=(ImageButton) view.findViewById(R.id.fragment_details_doctor_service_button);
        bTime=(ImageButton) view.findViewById(R.id.fragment_details_doctor_ouverture_button);
        tSpecialité=(TextView) view.findViewById(R.id.fragment_details_doctor_specialist);
        tService=(TextView) view.findViewById(R.id.fragment_details_doctor_service);
        tTime=(TextView) view.findViewById(R.id.fragment_details_doctor_overture);
        specLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_specialist_linearlayout);
        serviceLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_service_linearlayout);
        timeLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_ouverture_linearlayout);
        typeLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_type_linearlayout);

        bPhone = (ImageButton) view.findViewById(R.id.details_doctor_phone);
        bMessage = (ImageButton) view.findViewById(R.id.details_doctor_messsage);

        specLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        serviceLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        timeLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        typeLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));


        //
        bPhone.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fall_down_retard));
        bMessage.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fall_down_retard));
        //initialiser les views
        initialeTheViewIntent();

        //performer les onClicks
        bService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tService.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tTime.getText().toString(), Toast.LENGTH_SHORT).show();
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


        tSpecialité.setText(mCurrentDoctor.getSpec());
        tService.setText(WordUtils.capitalize(mCurrentDoctor.getService()));
        tTime.setText(mCurrentDoctor.getTime());

        db.close();
    }


}
