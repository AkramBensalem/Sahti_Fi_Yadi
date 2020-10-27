package com.gadg.sahtifiyadi.login.profiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gadg.sahtifiyadi.login.addUtilisateur.addNormalUserFragment;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getType;

public class ProfileTablayoutAdapter extends FragmentPagerAdapter {

    int mNumberOfFragment;
    Context myContext;

    public ProfileTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new updateNormalUserFragment();
            case 1:
                String type = getType(myContext);
                if (type.equals("1")){
                    return new updateDoctorFragment();
                }else if (type.equals("2")) {
                    return new updateNormalUserFragment();
                }else if (type.equals("3")){
                    return new updateHospitalFragment();
                }else if (type.equals("4")){
                    return new updateLaboFragment();
                }

            default:
                return null;}
    }

    @Nullable


    @Override
    public int getCount() {
        return mNumberOfFragment;    }
}
