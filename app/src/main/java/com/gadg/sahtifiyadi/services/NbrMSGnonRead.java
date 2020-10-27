package com.gadg.sahtifiyadi.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.auth.FirebaseAuth;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getNbrMessageNoRead;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getNbrMsgs;


public class NbrMSGnonRead extends IntentService {

    public NbrMSGnonRead() {
        super("NbrMSGnonRead");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Integer count = 0;
        if (intent != null &&   (FirebaseAuth.getInstance().getCurrentUser()!= null )) {
            getNbrMessageNoRead(getBaseContext());
            Intent intent1 = new Intent("com.example.myapplication");
            intent1.putExtra("NbrMsgs",getNbrMsgs(this));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            }
        }

}
