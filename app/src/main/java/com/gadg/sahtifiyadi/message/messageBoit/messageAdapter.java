package com.gadg.sahtifiyadi.message.messageBoit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.TablesColumnsNames;
import com.gadg.sahtifiyadi.items.MessageItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Message_columns.*;
import static com.gadg.sahtifiyadi.utilities.tools.DiffrenceDate;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {

    public final static String TAG = messageAdapter.class.getSimpleName();

    public GradientDrawable mGradientDrawable;
    private Activity mActivity;
    public Cursor mMessageCursor;

    private final LayoutInflater mLayoutInflater;

    private int mMessagePos;
    private int mMessageFirabasePos;
    private int mMessageSenderNamePos;
    private int mMessageShortPos;
    private int mMessageDatePos;
    private int mMessageStatusPos;
    private int mMessageImagePos;

    public messageAdapter(Activity activity, Cursor cursor){
        this.mMessageCursor = cursor;
        this.mActivity = activity;
        mLayoutInflater = LayoutInflater.from(this.mActivity.getBaseContext());

        populateColumnPositions();

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mActivity, R.drawable.profile);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

    }


    private void populateColumnPositions() {
        if(mMessageCursor == null)
            return;
        // Get column indexes from mCursor
        mMessagePos = mMessageCursor.getColumnIndex(_ID);
        mMessageFirabasePos = mMessageCursor.getColumnIndex(_ID_FIREBASE);
        mMessageSenderNamePos = mMessageCursor.getColumnIndex(_NAME);
        mMessageShortPos = mMessageCursor.getColumnIndex(RECENT_MESSAGE);
        mMessageDatePos = mMessageCursor.getColumnIndex(MESSAGE_RECENT_DATE);
        mMessageStatusPos = mMessageCursor.getColumnIndex(IS_READ);
        mMessageImagePos = mMessageCursor.getColumnIndex(_IMAGE);

    }

    public void changeCursor(Cursor cursor) {
        if(mMessageCursor != null)
            mMessageCursor.close();
        mMessageCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(mActivity, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        if (position < 20) {

            mMessageCursor.moveToPosition(position);

            String image = mMessageCursor.getString(mMessageImagePos);
            if (image.equals("R.drawable.profile")){
                Glide.with(mActivity.getBaseContext())
                        .load(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mHopitalImage);

            } else {
                Glide.with(mActivity.getBaseContext())
                        .load(image)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(holder.mHopitalImage);

            }
            String senderName = mMessageCursor.getString(mMessageSenderNamePos);
            String recentMessage = mMessageCursor.getString(mMessageShortPos);
            String date = mMessageCursor.getString(mMessageDatePos);
            boolean status = mMessageCursor.getString(mMessageStatusPos).equals("false");
            if (status){
                holder.mMessageRecentMessage.setTextColor(Color.GRAY);
                holder.mMessageRecentMessage.setTypeface(Typeface.DEFAULT_BOLD);
            }

            holder.mMessageSenderName.setText(WordUtils.capitalizeFully(senderName));
            holder.mMessageRecentMessage.setText(WordUtils.capitalizeFully(recentMessage));

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


            try {
                holder.mHopitalContactTextView.setText(DiffrenceDate(format.parse(date), Calendar.getInstance().getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                holder.mHopitalContactTextView.setText(date);
            }

            holder.mId =  mMessageCursor.getInt(mMessagePos);
            holder.mFireBase = mMessageCursor.getString(mMessageFirabasePos);
            holder.image = image;
        }

    }

    @Override
    public int getItemCount() {
        return mMessageCursor == null ? 0 : mMessageCursor.getCount() > 20 ? 20 : mMessageCursor.getCount();
    }

        class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView mMessageSenderName, mMessageRecentMessage, mHopitalContactTextView;
            public ImageView mHopitalImage;
            public int mId;
            public String mFireBase, image;
            private Context mCont;
            public GradientDrawable mGradientDrawable;
            private Activity activity;

            private FirebaseUser firebaseUser;


            public MessageViewHolder(Activity activity, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
                mMessageSenderName = (TextView) itemView.findViewById(R.id.name_message_sender);
                mMessageRecentMessage = (TextView) itemView.findViewById(R.id.messageText);
                mHopitalImage = (ImageView) itemView.findViewById(R.id.sender_image);
                mHopitalContactTextView = (TextView) itemView.findViewById(R.id.dateTest);
            this.activity = activity;
            mCont = activity.getBaseContext();
            mGradientDrawable = gradientDrawable;


                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (firebaseUser== null|| !firebaseUser.getUid().equals(mFireBase)) {
                Intent intent = MessageItem.starter(mCont,mFireBase, mMessageSenderName.getText().toString(), image);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCont.startActivity(intent);
                activity.overridePendingTransition(R.anim.open_enter,R.anim.nav_default_exit_anim);
               }
        }
    }
}
