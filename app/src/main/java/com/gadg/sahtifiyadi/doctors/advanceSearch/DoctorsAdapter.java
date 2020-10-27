package com.gadg.sahtifiyadi.doctors.advanceSearch;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DatabaseHelper;
import com.gadg.sahtifiyadi.database.TablesColumnsNames;
import com.gadg.sahtifiyadi.doctors.doctorDetails.Details_Doctor;
import com.gadg.sahtifiyadi.login.profiles.MainProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Doctor_columns.*;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorSViewHolder> {

    private GradientDrawable mGradientDrawable;
    private Cursor mDoctorsCursor;
    private Context mContext;

    private final LayoutInflater mLayoutInflater;

    private int mDoctorPos;
    private int mDoctotFirabasePos;
    private int mDoctorNamePos;
    private int mDoctorPlacePos;
    private int mDoctorNumberPos;
    private int mDoctorImagePos;

    public DoctorsAdapter(Context context, Cursor cursor) {
        this.mDoctorsCursor = cursor;
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
        if(mDoctorsCursor == null)
            return;
        // Get column indexes from mCursor
        mDoctorPos = mDoctorsCursor.getColumnIndex(_ID);
        mDoctotFirabasePos = mDoctorsCursor.getColumnIndex(_ID_FIREBASE);
        mDoctorNamePos = mDoctorsCursor.getColumnIndex(_NAME);
        mDoctorPlacePos = mDoctorsCursor.getColumnIndex(_PLACE);
        mDoctorNumberPos = mDoctorsCursor.getColumnIndex(_SPECIALITY);
        mDoctorImagePos = mDoctorsCursor.getColumnIndex(_IMAGE);

    }
    public void changeCursor(Cursor cursor) {
        if(mDoctorsCursor != null)
            mDoctorsCursor.close();
        mDoctorsCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @Override
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.user_item, parent, false);
        return new DoctorSViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorSViewHolder  holder, int position) {
        if (position < 20) {
            mDoctorsCursor.moveToPosition(position);

            String imageUrl = mDoctorsCursor.getString(mDoctorImagePos);
            if (imageUrl.equals("R.drawable.profile")){
                Glide.with(mContext)
                        .load(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mDoctorImage);

            }else {
                Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mDoctorImage);
            }
            String Name = WordUtils.capitalizeFully(mDoctorsCursor.getString(mDoctorNamePos));
            String Place = WordUtils.capitalizeFully(mDoctorsCursor.getString(mDoctorPlacePos));
            String Phone = WordUtils.capitalizeFully(mDoctorsCursor.getString(mDoctorNumberPos));

            int id = mDoctorsCursor.getInt(mDoctorPos);
            String FireBaseId = mDoctorsCursor.getString(mDoctotFirabasePos);


            holder.mDoctorNameTextView.setText(Name);
            holder.mDoctorPlaceTextView.setText(Place);
            holder.mDoctorSpecialiteTextView.setText(Phone);

            holder.mId = id;
            holder.mFireBaseId = FireBaseId;


            holder.backGroundLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down));
            holder.mDoctorImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down_retard));
        }
    }

    @Override
    public int getItemCount() {
        return mDoctorsCursor == null ? 0 : mDoctorsCursor.getCount() > 20 ? 20 : mDoctorsCursor.getCount();

    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mDoctorNameTextView, mDoctorPlaceTextView, mDoctorSpecialiteTextView;
        public ImageView mDoctorImage;
        public int mId;
        public String mFireBaseId;
        public GradientDrawable mGradientDrawable;



        public CardView backGroundLayout;

        private Context mCont;
        private FirebaseUser firebaseUser;

        DoctorSViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            backGroundLayout = (CardView) itemView.findViewById(R.id.doctor_cordinator_background);
            mDoctorImage = (ImageView) itemView.findViewById(R.id.user_image);
            mDoctorNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            mDoctorPlaceTextView = (TextView) itemView.findViewById(R.id.user_place) ;
            mDoctorSpecialiteTextView =(TextView) itemView.findViewById(R.id.user_other);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            itemView.setOnClickListener(this);
        }




        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
           //     if (firebaseUser== null|| !firebaseUser.getUid().equals(mFireBaseId)) {

                    Intent detailIntent = new Intent(mCont, Details_Doctor.class);
                    detailIntent.putExtra("id",mId);
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                        android.util.Pair<View, String> p1 = android.util.Pair.create((View)mDoctorImage,"DOCTORIMAGE");
                        android.util.Pair<View, String> p2 = android.util.Pair.create((View)mDoctorNameTextView,"DOCTORNAME");
                        options = ActivityOptions.makeSceneTransitionAnimation((Activity) mCont, p1 ,p2);
                    }
                    mCont.startActivity(detailIntent, options.toBundle());
             /*   } else {
                    mCont.startActivity(new Intent(mCont, MainProfile.class));
                }

              */

            }


        }
}
