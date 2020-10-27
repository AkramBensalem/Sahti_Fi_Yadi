package com.gadg.sahtifiyadi.ui.don_de_sang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;

import com.gadg.sahtifiyadi.login.LoginActivity;
import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;
import com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

public class DonateurAdapter extends RecyclerView.Adapter<DonateurAdapter.DonateursViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<don_de_song> mdonateur;
    private Context mContext;
    private ArrayList<don_de_song> mdonateurArray = new ArrayList<>();

    public DonateurAdapter(Context context, ArrayList<don_de_song> laboData) {
        this.mdonateur = laboData;
        this.mContext = context;
        this.mdonateurArray.addAll(laboData);


        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.medicament);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    public void filter(String text) {
        if(text.isEmpty()){
            mdonateur.clear();
            mdonateur.addAll(mdonateurArray);
        } else{
            ArrayList<don_de_song> result = new ArrayList<>();
            text = text.toLowerCase();
            for(don_de_song item: mdonateurArray){
                if(item.getGrsanguin().contains(text) ||
                        item.getAdressd().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mdonateur.clear();
            mdonateur.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public DonateurAdapter.DonateursViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.donateur_item, parent, false);
        return new DonateurAdapter.DonateursViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(DonateursViewHolder holder, int position) {
        don_de_song currentdonateur = mdonateur.get(position);
        holder.bindTo(currentdonateur);
    }


    @Override
    public int getItemCount() {
        return mdonateur.size();
    }

    static class DonateursViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText ,mPlaceText,mAge,grsnaguin;
        private ImageButton mphone, mMSG;
        private ImageView mDonateurImage;
        private Context mCont;
        private don_de_song mCurrentdonateur;


        private String phone;
        private String firebaseId;
        private String imageUrl;



        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        public static final String RECIVER = "Reciver";
        public static final String SENDER = "sender";
        public static final String RECIVER_IMAGE = "ReciverImageUrl";



        DonateursViewHolder(final Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            mDonateurImage = (ImageView) itemView.findViewById(R.id.donateur_imageinit);

            mNameText = (TextView) itemView.findViewById(R.id.donateur_name);
            mPlaceText = (TextView) itemView.findViewById(R.id.donateur_place) ;
            grsnaguin =(TextView) itemView.findViewById(R.id.donateur_grsanguin);
            mAge  =(TextView) itemView.findViewById(R.id.agedonateur);


            mphone =(ImageButton) itemView.findViewById(R.id.donnateur_phone);
            mMSG =(ImageButton) itemView.findViewById(R.id.donnateur_message);



            //
            mphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //téléphoner
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone.replaceAll(" ","")));
                    mCont.startActivity(i);
                }
            });
            mMSG.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //aller aux chat message
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                   Intent i = new Intent(mCont, chatRoom.class);
                  i.putExtra(RECIVER, firebaseId);
                    i.putExtra(RECIVER_IMAGE, imageUrl);
                  i.putExtra(SENDER, mNameText.getText().toString());
                    mCont.startActivity(i);
                    }else {
                        //
                        new AlertDialog.Builder(mCont)
                                .setTitle("Service non disponible")
                                .setMessage("Vous devez vous connecter d'abord!")
                                .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mCont.startActivity(new Intent(mCont, LoginActivity.class));

                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_baseline_not_interested_red)
                                .show();
                    }
                }
            });



            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(don_de_song Currentdonateur) {
            mCurrentdonateur = Currentdonateur;

           Glide.with(mCont).load(mCurrentdonateur.getImaged())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(mDonateurImage);

            mNameText.setText(Currentdonateur.getFullname());
            mPlaceText.setText(Currentdonateur.getAdressd());
            mAge.setText(Currentdonateur.getAge()+" ans");
            grsnaguin.setText((Currentdonateur.getGrsanguin()));

           phone = Currentdonateur.getContact();
           firebaseId = Currentdonateur.get_ID_firebase();
           imageUrl = Currentdonateur.getImaged();
        }


        @Override
        public void onClick(View view) {

        }


    }
}