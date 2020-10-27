package com.gadg.sahtifiyadi.ui.don_de_sang;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;
import com.google.android.material.tabs.TabLayout;

public class don_de_sangFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_de_sang, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout1donateur);
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerdonateur);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       donDeSangTablayoutAdapter mAdapter = new donDeSangTablayoutAdapter(getContext(), getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_person_add_24);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.redTransparent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.redTransparent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ((MainActivity) getActivity()).updateStatusBarColor("#E2B80707");
        ((MainActivity) getActivity()).updateActionBarColorTWO("#E2DD2525");
        return view;
    }

}