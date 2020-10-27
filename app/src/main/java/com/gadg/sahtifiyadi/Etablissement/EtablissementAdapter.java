package com.gadg.sahtifiyadi.Etablissement;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.TablesColumnsNames;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns.*;


public class EtablissementAdapter extends RecyclerView.Adapter<EtablissementAdapter.HopitalsViewHolder> {

    public GradientDrawable mGradientDrawable;
    public Cursor mHopitalsCursor;
    public static Context mContext;
    private int mTypeEtablissement;

    private final LayoutInflater mLayoutInflater;

    private int mHopitalPos;
    private int mHopitalFirabasePos;
    private int mHopitalNamePos;
    private int mHopitalPlacePos;
    private int mHopitalWilayaPos;
    private int mHopitalCommunePos;
    private int mHopitalNumberPos;
    private int mHopitalImagePos;




    public EtablissementAdapter(Context context, Cursor cursor, int type) {
        this.mHopitalsCursor = cursor;
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
        mTypeEtablissement = type;
    }


    private void populateColumnPositions() {
        if(mHopitalsCursor == null)
            return;
        // Get column indexes from mCursor
        mHopitalPos = mHopitalsCursor.getColumnIndex(_ID);
        mHopitalFirabasePos = mHopitalsCursor.getColumnIndex(_ID_FIREBASE);
        mHopitalNamePos = mHopitalsCursor.getColumnIndex(_NAME);
        mHopitalPlacePos = mHopitalsCursor.getColumnIndex(_PLACE);
        mHopitalWilayaPos = mHopitalsCursor.getColumnIndex(_WILAYA);
        mHopitalCommunePos = mHopitalsCursor.getColumnIndex(_COMMUNE);
        mHopitalNumberPos = mHopitalsCursor.getColumnIndex(_PHONE);
        mHopitalImagePos = mHopitalsCursor.getColumnIndex(_IMAGE);

    }

    public void changeCursor(Cursor cursor) {
        if(mHopitalsCursor != null)
            mHopitalsCursor.close();
        mHopitalsCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @Override
    public HopitalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.user_item, parent, false);
        return new HopitalsViewHolder(mContext, view, mTypeEtablissement,mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(HopitalsViewHolder holder, int position) {
        if (position < 20) {

            mHopitalsCursor.moveToPosition(position);
            String imageUrl = mHopitalsCursor.getString(mHopitalImagePos);
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



            String Name = WordUtils.capitalizeFully(mHopitalsCursor.getString(mHopitalNamePos));
            String Place = WordUtils.capitalizeFully(mHopitalsCursor.getString(mHopitalPlacePos));
            String Phone = WordUtils.capitalizeFully(mHopitalsCursor.getString(mHopitalNumberPos));

            int id = mHopitalsCursor.getInt(mHopitalPos);
            String FireBaseId = mHopitalsCursor.getString(mHopitalFirabasePos);


            holder.mHopitalNameTextView.setText(Name);
            holder.mHopitalPlaceTextView.setText(Place);
            holder.mHopitalContactTextView.setText(Phone);

            holder.mId = id;

            holder.mFireBaseId = FireBaseId;


            holder.backGroundLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down));
            holder.mHopitalImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fall_down_retard));

        }
    }


    @Override
    public int getItemCount() {
        return mHopitalsCursor == null ? 0 : mHopitalsCursor.getCount() > 20 ? 20 : mHopitalsCursor.getCount();
    }

    static class HopitalsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mHopitalNameTextView, mHopitalPlaceTextView, mHopitalContactTextView;
        public ImageView mHopitalImage;
        public int mId,mType;
        public String mFireBaseId;

        private Context mCont;
        public GradientDrawable mGradientDrawable;

        private FirebaseUser firebaseUser;

        public CardView backGroundLayout;



        HopitalsViewHolder(Context context, View itemView, int type,GradientDrawable gradientDrawable) {
            super(itemView);
            backGroundLayout = (CardView) itemView.findViewById(R.id.doctor_cordinator_background);
            mHopitalNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            mHopitalPlaceTextView = (TextView) itemView.findViewById(R.id.user_place);
            mHopitalContactTextView = (TextView) itemView.findViewById(R.id.user_other);
            mHopitalImage = (ImageView) itemView.findViewById(R.id.user_image);
            mCont = context;
            mType = type;
            mGradientDrawable = gradientDrawable;
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (firebaseUser== null|| !firebaseUser.getUid().equals(mFireBaseId)) {
            Intent detailIntent = new Intent(mCont, EtablissementProfileActivity.class);
            detailIntent.putExtra("id",mId);
            detailIntent.putExtra("type",mType);
                Log.d("idandtypetest","id "+mId+" type= "+mType);
            ActivityOptions options = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptions.makeSceneTransitionAnimation((Activity) mCont,  Pair.create((View)mHopitalImage,"I"));
            }
            mCont.startActivity(detailIntent, options.toBundle());
            } else {
               // mCont.startActivity(new Intent(mCont, MainProfile.class));
                Toast.makeText(mCont,"Go to your self profile",Toast.LENGTH_LONG).show();
            }
        }
    }
}