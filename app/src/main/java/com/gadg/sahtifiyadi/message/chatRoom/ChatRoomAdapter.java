package com.gadg.sahtifiyadi.message.chatRoom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.items.MessageChatItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.gadg.sahtifiyadi.utilities.tools.DiffrenceDate;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MessageViewHolder> {

    public final static String TAG = ChatRoomAdapter.class.getSimpleName();

    public GradientDrawable mGradientDrawable;
    public ArrayList<MessageChatItem> mMessagesData;
    public Context mContext;
    public ChatRoomAdapter(Context context, ArrayList<MessageChatItem> message){
        this.mMessagesData = message;
        this.mContext = context;
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.doctorm);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_room_message_item, parent, false);
        return new MessageViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        //Get the current sport
        MessageChatItem currentMessage = mMessagesData.get(position);
        //Bind the data to the views
        holder.bindTo(currentMessage);

    }

    @Override
    public int getItemCount() {
        return mMessagesData.size();    }

        class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView mSenderNameTextView;
        public TextView mMessageTextView, mDateTest;
        public ImageView mSenderImage,mImageMessage;
        public Context mCont;
        public MessageChatItem mCurrentMessage;
        public GradientDrawable mGradientDrawable;
        private LinearLayout petitLinear;


        public MessageViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);


            mCont = context;
            //Initialize the views
            mSenderNameTextView = (TextView) itemView.findViewById(R.id.name_message_chat_sender);
            mMessageTextView = (TextView) itemView.findViewById(R.id.message_chat_text);
            mSenderImage = (ImageView) itemView.findViewById(R.id.message_chat_image);
            mDateTest = (TextView) itemView.findViewById(R.id.message_chat_date);
            mImageMessage = (ImageView) itemView.findViewById(R.id.message_chat_image_sender);
            petitLinear = itemView.findViewById(R.id.chat_room_item_petit_message);

            petitLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mCont,"Copié",Toast.LENGTH_SHORT).show();
                }
            });


            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
        }



        public void bindTo(MessageChatItem currentMessage) {

            mCurrentMessage = currentMessage;
            mSenderNameTextView.setText(currentMessage.getMsgName());
            String msg = currentMessage.getMessage();
            if (!msg.contains("5I5@")){
            mMessageTextView.setText(msg);
            }else{
                mImageMessage.setVisibility(View.VISIBLE);
                Glide.with(mCont).load(msg.replaceAll("5I5@",""))
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(mImageMessage);
                mMessageTextView.setVisibility(View.GONE);

            }
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


            try {
                mDateTest.setText(DiffrenceDate(format.parse(currentMessage.getDate()), Calendar.getInstance().getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                mDateTest.setText(currentMessage.getDate());
            }

            Glide.with(mCont).load(mCurrentMessage.getImageResource())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(mSenderImage);
        }

    }
}
