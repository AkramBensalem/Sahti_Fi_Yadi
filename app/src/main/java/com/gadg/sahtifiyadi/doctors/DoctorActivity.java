package com.gadg.sahtifiyadi.doctors;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gadg.sahtifiyadi.R;
import com.google.android.material.tabs.TabLayout;

public class DoctorActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        viewPager = (ViewPager) findViewById(R.id.dviewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        DoctorTablayoutAdapter myAdapter = new DoctorTablayoutAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
      //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_round_list_24);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_round_people_outline_24);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        ViewPager tabHost = (ViewPager) findViewById(R.id.dviewpager);
        if (tabHost.getCurrentItem() == 0) super.onBackPressed();
       else tabHost.setCurrentItem(tabHost.getCurrentItem()-1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if (id == R.id.nav_home){
           onBackPressed();
       }

        return super.onOptionsItemSelected(item);
    }
}
