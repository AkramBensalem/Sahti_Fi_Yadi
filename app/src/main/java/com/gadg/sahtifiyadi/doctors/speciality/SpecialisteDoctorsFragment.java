package com.gadg.sahtifiyadi.doctors.speciality;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerSpeciality;
import com.gadg.sahtifiyadi.items.DoctorsSpecialistes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.utilities.tools.initialSpecDB;
import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class SpecialisteDoctorsFragment extends Fragment {

    public static   DoctorsSpecialisteAdapter mAdapter;
    public   String[] specialisteNames;
    public   TypedArray doctorSpecialistesImages;
    private  ArrayList<DoctorsSpecialistes> mDoctorsSpecialistes = new ArrayList<DoctorsSpecialistes>();
    private static ArrayList<String> mSpecialistesCount = new ArrayList<String>();

    public SpecialisteDoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
        final RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        specialisteNames = getResources().getStringArray(R.array.speciality);
        doctorSpecialistesImages = getResources().obtainTypedArray(R.array.speciality_images);
        if (isNetworkAvailable(getContext())>0) {
            getNumberOfEverySpec(mDoctorsSpecialistes, specialisteNames, mSpecialistesCount, doctorSpecialistesImages);
        }else {
            for (int i = 0; i <specialisteNames.length ; i++) {
                mDoctorsSpecialistes.add(new DoctorsSpecialistes(specialisteNames[i],"+10",doctorSpecialistesImages.getResourceId(i,0)));
            }
            doctorSpecialistesImages.recycle();
        }
        mAdapter = new DoctorsSpecialisteAdapter( getActivity(),getActivity(), mDoctorsSpecialistes);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public static void getNumberOfEverySpec(final ArrayList<DoctorsSpecialistes> arrayDoctors, final String[] arrayNames, final ArrayList<String> arrayCount, final TypedArray arrayImage ){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference specRef = rootRef.child("Specialities");
        final   int[] counter = {0};
        specRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds : dataSnapshot.getChildren()){
                    //array.add( ds.getValue(String.class));
                    arrayCount.add(ds.getValue(String.class));
                    arrayDoctors.add(new DoctorsSpecialistes(arrayNames[counter[0]],ds.getValue(String.class),arrayImage.getResourceId(counter[0],0)));
                    counter[0]++;
                }
                Log.d("specialityTest","arraylength "+ arrayDoctors.size());
                arrayImage.recycle();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("specialityTest","error "+ databaseError.getMessage());
            }
        });

    }


}