package com.gadg.sahtifiyadi.ui.don_de_sang;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gadg.sahtifiyadi.ui.a_propos_de_nous.A_propos_de_nousFragment;

public class donDeSangTablayoutAdapter extends FragmentStatePagerAdapter {
    int mNumberOfFragment = 2;
    Context myContext;
    public donDeSangTablayoutAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Blooddonor();
            case 1:
               return new findadonor();
            default:
                return null;}
    }


    @Override
    public int getCount() {
        return mNumberOfFragment;    }

}

