package com.gadg.sahtifiyadi.login.addUtilisateur;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class insertionTablayoutAdapter extends FragmentPagerAdapter {

    int mNumberOfFragment;
    Context myContext;

    public insertionTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new addNormalUserFragment();
            case 1:
                return new AddDoctorFragment();
            case 2:
                return new AddPharmacieFragment();
            case 3:
                return new AddHospitalFragment();
            case 4:
                return new AddLaboFragment();
            default:
                return null;}
    }

    @Nullable


    @Override
    public int getCount() {
        return mNumberOfFragment;    }
}
