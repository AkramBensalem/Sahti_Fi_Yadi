package com.gadg.sahtifiyadi.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.gadg.sahtifiyadi.database.DBManagerMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;



public class UpdateMsgDB extends IntentService {
    public final static String TAG = UpdateMsgDB.class.getSimpleName();

    private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Messages");
    private DBManagerMessage db;
    private FirebaseUser user;
    private  java.util.Date date;

    public UpdateMsgDB() {
        super("UpdateMsgDB");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("ServiceAkramTestt","onHandleIntent started ");

        final Intent intent2 = new Intent("com.example.myapplication.message");
        db = new DBManagerMessage(getBaseContext());
        db.open();
        if (intent != null &&   (FirebaseAuth.getInstance().getCurrentUser()!= null )) {
            user= FirebaseAuth.getInstance().getInstance().getCurrentUser();
            firebaseDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    db.deleteAll();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists()&& ds.child("ID_Reciver").exists()&& ds.child("Is_Readed").exists()&& ds.child("Date").exists()&& ds.child("AllMsg").exists();
                        if (is_exist) {
                            String SenderName = ds.child("Sender_Name").getValue(String.class);
                            String SenderImage;
                            if (ds.child("Sender_Image").exists()) {
                                SenderImage = ds.child("Sender_Image").getValue(String.class);
                            }else SenderImage = "R.drawable.doctorm";
                            String message = ds.child("message_envoyer").getValue(String.class);
                            String fullMsg = ds.child("AllMsg").getValue(String.class);
                            String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                            String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                            String Date = ds.child("Date").getValue(String.class);
                            SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
                            try {
                                date = format.parse(Date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!ID_firebase.equals(user.getUid())) {
                                if(db.CheckIsDataAlreadyInDBorNot(ID_firebase)){
                                    Log.d("ServiceAkramTestt","update ");

                                    db.update(ID_firebase,SenderName,message,fullMsg,Date,Is_Readed,SenderImage);
                                }else{
                                    Log.d("ServiceAkramTestt","inserted ");

                                    db.insert(ID_firebase,SenderName,message,fullMsg,Date,Is_Readed,SenderImage);

                                }
                            }
                        }
                    }
                    db.close();
                    intent2.putExtra("refresh",true);
                    Log.d("ServiceAkramTestt","fine ");

                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent2);

                             }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, databaseError.getMessage());
                    db.close();
                    intent2.putExtra("refresh",false);
                    Log.d("ServiceAkramTestt","not fine ");

                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent2);

                }
            });
            }
        }

}
