package com.gadg.sahtifiyadi.pharmacies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.TablesColumnsNames;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Pharmacy_columns.*;

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.pharmaciesViewHolder> {
    private GradientDrawable mGradientDrawable;
    private Context mContext;
    public Cursor mPharmacieCursor;
    private final LayoutInflater mLayoutInflater;

    private int mPharmaciePos;
    private int mPharmacieFirabasePos;
    private int mPharmacieNamePos;
    private int mPharmaciePlacePos;
    private int mPharmacieWilayaPos;
    private int mPharmacieCommunePos;
    private int mPharmacieNumberPos;
    private int mPharmacieImagePos;


    pharmacyAdapter(Context context,  Cursor cursor) {
        this.mPharmacieCursor = cursor;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(this.mContext);

        populateColumnPositions();

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.profile);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    private void populateColumnPositions() {
        if(mPharmacieCursor == null)
            return;
        // Get column indexes from mCursor
        mPharmaciePos = mPharmacieCursor.getColumnIndex(_ID);
        mPharmacieFirabasePos = mPharmacieCursor.getColumnIndex(_ID_FIREBASE);
        mPharmacieNamePos = mPharmacieCursor.getColumnIndex(_NAME);
        mPharmaciePlacePos = mPharmacieCursor.getColumnIndex(_PLACE);
        mPharmacieWilayaPos = mPharmacieCursor.getColumnIndex(_WILAYA);
        mPharmacieCommunePos = mPharmacieCursor.getColumnIndex(_COMMUNE);
        mPharmacieNumberPos = mPharmacieCursor.getColumnIndex(_PHONE);
        mPharmacieImagePos = mPharmacieCursor.getColumnIndex(_IMAGE);

    }

    public void changeCursor(Cursor cursor) {
        if(mPharmacieCursor != null)
            mPharmacieCursor.close();
        mPharmacieCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @Override
    public pharmaciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =mLayoutInflater.inflate(R.layout.pharmacy_item, parent, false);
        return new pharmaciesViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(pharmaciesViewHolder holder, int position) {
        if (position < 20) {

            mPharmacieCursor.moveToPosition(position);
            String imageUrl = mPharmacieCursor.getString(mPharmacieImagePos);
            if (imageUrl.equals("R.drawable.profile")){
                Glide.with(mContext)
                        .load(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mHopitalImage);

            }else {
                Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mHopitalImage);
            }



            String Name = WordUtils.capitalizeFully(mPharmacieCursor.getString(mPharmacieNamePos));
            String Place = WordUtils.capitalizeFully(mPharmacieCursor.getString(mPharmaciePlacePos));
            String Phone = WordUtils.capitalizeFully(mPharmacieCursor.getString(mPharmacieNumberPos));

            int id = mPharmacieCursor.getInt(mPharmaciePos);
            String FireBaseId = mPharmacieCursor.getString(mPharmacieFirabasePos);


            holder.mPharmacieNameTextView.setText(Name);
            holder.mPharmaciePlaceTextView.setText(Place);
            holder.mPharmacieContactTextView.setText(Phone);

            holder.mId = id;
            holder.mFireBaseId = FireBaseId;


            holder.backGroundLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down));
            holder.mHopitalImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down_retard));

        }
    }


    @Override
    public int getItemCount() {
        return mPharmacieCursor == null ? 0 : mPharmacieCursor.getCount() > 20 ? 20 : mPharmacieCursor.getCount();
    }

    static class pharmaciesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        public TextView mPharmacieNameTextView, mPharmaciePlaceTextView, mPharmacieContactTextView;
        public ImageView mHopitalImage;
        public int mId;
        public String mFireBaseId;

        private Context mCont;
        public GradientDrawable mGradientDrawable;

        private FirebaseUser firebaseUser;

        public CardView backGroundLayout;

        pharmaciesViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            backGroundLayout = (CardView) itemView.findViewById(R.id.pharmacy_cordinator_background);
            mPharmacieNameTextView = (TextView) itemView.findViewById(R.id.pharmacy_name);
            mPharmaciePlaceTextView = (TextView) itemView.findViewById(R.id.pharmacy_place);
            mPharmacieContactTextView = (TextView) itemView.findViewById(R.id.pharmacy_number);
            mHopitalImage = (ImageView) itemView.findViewById(R.id.pharmacy_image);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent=new Intent(mCont,pharmacies_info.class);
            intent.putExtra("id",mId);
            mCont.startActivity(intent);


        }
    }
}