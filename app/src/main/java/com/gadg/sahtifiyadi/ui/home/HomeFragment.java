package com.gadg.sahtifiyadi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gadg.sahtifiyadi.Etablissement.HopitalActivity;
import com.gadg.sahtifiyadi.Etablissement.LaboActivity;
import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.doctors.DoctorActivity;
import com.gadg.sahtifiyadi.pharmacies.pharmacyActivity;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragmentAkramTest1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).updateStatusBarColor("#295171");
        ((MainActivity) getActivity()).updateActionBarColorTWO("#6399d5");
        Log.d(TAG, "Before Button");
        root.findViewById(R.id.doctor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoctorActivity.class));
                getActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
            }
        });
        Log.d(TAG, "Before Button");

        root.findViewById(R.id.hospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HopitalActivity.class));
                getActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
            }
        });
        root.findViewById(R.id.labo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LaboActivity.class));
                getActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
            }
        });
        root.findViewById(R.id.pharmacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), pharmacyActivity.class));
                getActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
            }
        });
        return root;
    }

}