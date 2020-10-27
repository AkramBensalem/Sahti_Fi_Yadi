package com.gadg.sahtifiyadi.doctors;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gadg.sahtifiyadi.doctors.advanceSearch.AdvanceSearchDoctorFragment;
import com.gadg.sahtifiyadi.doctors.speciality.SpecialisteDoctorsFragment;

public class DoctorTablayoutAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS= 2;
    private Context myContext;

    public DoctorTablayoutAdapter(Context context,@NonNull FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SpecialisteDoctorsFragment();
            case 1:
                return new AdvanceSearchDoctorFragment();
            default:
                return null;}
    }

    @Nullable
    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;    }
}
