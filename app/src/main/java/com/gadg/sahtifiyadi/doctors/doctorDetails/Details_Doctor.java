package com.gadg.sahtifiyadi.doctors.doctorDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerDoctor;
import com.gadg.sahtifiyadi.items.Doctors;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.lang3.text.WordUtils;

import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class Details_Doctor extends AppCompatActivity {
    //Les constants


    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";


    private TabLayout tabLayout;
    private ViewPager viewPager;


    private ImageView DoctorImage;
    private TextView DoctorName;

    private String mFireBaseId;
    private Doctors mCurrentDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_doctor);



        tabLayout = (TabLayout) findViewById(R.id.details_doctor_tablayout);
        viewPager = (ViewPager) findViewById(R.id.details_doctor_viewpager);
        DoctorImage = (ImageView) findViewById(R.id.details_doctor_image);
        DoctorName = (TextView) findViewById(R.id.details_doctor_Name);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);





        //
        DetailsDoctorTablayoutAdapter myAdapter = new DetailsDoctorTablayoutAdapter(this, getSupportFragmentManager(), 2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);


        initialView();


        //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_round_person_outline_24);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_person_pin_circle_24);
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


    private void initialView() {
        DBManagerDoctor db = new DBManagerDoctor(this);
        db.open();

        Intent i = getIntent();

        int id =  i.getIntExtra("id",1);
        mCurrentDoctor = db.getDoctorFromID(id);
        String imageUrl = mCurrentDoctor.getImageUrl();
        if (imageUrl.equals("R.drawable.profile")){
            Log.d("testImage","imageUrl is R.drawable.profile" );
            Glide.with(this).load(R.drawable.profile).into(DoctorImage);
        }else {
            Log.d("testImage","imageUrl is " + imageUrl);
            Glide.with(this).load(mCurrentDoctor.getImageUrl()).into(DoctorImage);
        }

        DoctorName.setText(WordUtils.capitalizeFully(mCurrentDoctor.getNameDoctor()));
        db.close();
    }

    @Override
    public void onBackPressed() {
        ViewPager viewpager = (ViewPager) findViewById(R.id.details_doctor_viewpager);
        if (viewpager.getCurrentItem() == 0) super.onBackPressed();
        else viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.nav_home){
           onBackPressed();
       }else if(id == R.id.block_icon){
            if (isNetworkAvailable(getBaseContext())>0){
               //
                new AlertDialog.Builder(this)
                        .setTitle("Avertissement")
                        .setMessage("Vouillez vous vraiment bloquer cette personne ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),"Blocked",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.ic_round_perm_scan_wifi_24)
                        .show();

            }else {
                Toast.makeText(getBaseContext(),"un preblem avec connexion",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
