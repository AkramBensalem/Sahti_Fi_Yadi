package com.gadg.sahtifiyadi.login.profiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gadg.sahtifiyadi.R;
import com.google.android.material.tabs.TabLayout;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getType;


public class MainProfile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int nbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_profile);
        tabLayout = (TabLayout) findViewById(R.id.profile_tablayout);
        viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        String type = getType(getBaseContext());
        if (Integer.parseInt(type) == 0){
            nbr = 1;
        }else {
            nbr = 2;
        }

            ProfileTablayoutAdapter myAdapter = new ProfileTablayoutAdapter(this, getSupportFragmentManager(),nbr);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);



        //
        tabLayout.getTabAt(0).setIcon(R.drawable.user_icon);
        //  tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        switch (type){
            case "1" :
                tabLayout.getTabAt(1).setIcon(R.drawable.medcin_icon);
                //   tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                break;
            case "2":
                tabLayout.getTabAt(1).setIcon(R.drawable.pharmacy_icon);
                //  tabLayout.getTabAt(3).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                break;
            case "3":
                tabLayout.getTabAt(1).setIcon(R.drawable.hopital_icon);
                //  tabLayout.getTabAt(2).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                break;
            case "4":
                tabLayout.getTabAt(1).setIcon(R.drawable.labo_icon);
                //   tabLayout.getTabAt(4).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                break;
        }








        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
