package com.gadg.sahtifiyadi.ui.settings;

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
import androidx.preference.PreferenceFragmentCompat;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;

public class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ((MainActivity) getActivity()).updateStatusBarColor("#295171");
            ((MainActivity) getActivity()).updateActionBarColorTWO("#6399d5");
        }

}