package com.gadg.sahtifiyadi.doctors.speciality;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerSpeciality;
import com.gadg.sahtifiyadi.items.DoctorsSpecialistes;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveSpeciality;


class DoctorsSpecialisteAdapter extends RecyclerView.Adapter<DoctorsSpecialisteAdapter.DoctorSViewHolder> {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<DoctorsSpecialistes> mDoctorsspecialistes;
    private Context mContext;
    private Activity activity;

    DoctorsSpecialisteAdapter(Activity activity,Context context, ArrayList<DoctorsSpecialistes> doctorData) {
        this.mDoctorsspecialistes = doctorData;
        this.mContext = context;
           this.activity = activity;
        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.cardiologue);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    @Override
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specialite_item, parent, false);
        return new DoctorSViewHolder(activity,mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorSViewHolder  holder, int position) {
        //Get the current doctor
        DoctorsSpecialistes currentDoctor = mDoctorsspecialistes.get(position);
        //Bind the data to the views
        holder.bindTo(currentDoctor);

        Animation animation = AnimationUtils.loadAnimation(mContext,android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mDoctorsspecialistes.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameText, mNumberText;
        public ImageView mDoctorImage;
        private Context mCont;
        private Activity activity;
        private DoctorsSpecialistes mCurrentDoctor;
        private GradientDrawable mGradientDrawable;
        private DBManagerSpeciality db;

        DoctorSViewHolder(Activity activity, Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            //Initialize the views
            mNameText = (TextView) itemView.findViewById(R.id.specialite_name);
            mNumberText = (TextView) itemView.findViewById(R.id.specialite_times);
            mDoctorImage = (ImageView) itemView.findViewById(R.id.spec_doctor_image);
            mCont = context;
            this.activity = activity;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(DoctorsSpecialistes currentSpeciality) {
            Glide.with(mCont).load(currentSpeciality.getImageSpecialiste()).placeholder(mGradientDrawable).into(mDoctorImage);
            //Populate the textviews with data
            mNameText.setText(currentSpeciality.getSpecialiste());
         //   mNumberText.setText(db.getNumberSpec(currentSpeciality.getSpecialiste()));
            mNumberText.setText(currentSpeciality.getSpecialiteCount());
            //Get the current sport
            mCurrentDoctor = currentSpeciality;
        }

        @Override
        public void onClick(View view) {
            saveSpeciality(mCont,mNameText.getText().toString());
            ViewPager tabHost = (ViewPager) activity.findViewById(R.id.dviewpager);
            tabHost.setCurrentItem(tabHost.getCurrentItem()+1);
        }

    }
}
