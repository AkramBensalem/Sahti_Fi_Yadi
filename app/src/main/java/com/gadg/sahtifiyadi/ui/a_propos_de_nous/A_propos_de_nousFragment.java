package com.gadg.sahtifiyadi.ui.a_propos_de_nous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;

public class A_propos_de_nousFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_a_proos_de_nous, container, false);

        ((MainActivity) getActivity()).updateStatusBarColor("#295171");
        ((MainActivity) getActivity()).updateActionBarColorTWO("#6399d5");

        return root;
    }
}