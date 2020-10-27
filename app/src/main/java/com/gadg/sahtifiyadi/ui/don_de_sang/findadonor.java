package com.gadg.sahtifiyadi.ui.don_de_sang;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.gadg.sahtifiyadi.FireBaseCallBack;
import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerDonateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.utilities.tools.getCommuns;
import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class findadonor extends Fragment {

    public findadonor() {
        // Required empty public constructor
    }

    private ArrayList<don_de_song> mdonateurdata = new ArrayList<don_de_song>();
    private DBManagerDonateur dbManagerDonateur;
    private RecyclerView mRecyclerView;
    private DonateurAdapter mAdapter;
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("Donateurs");
    private ProgressDialog mProgressDialog;
    private RadioGroup mRadioGroup_1;
    private RadioGroup mRadioGroup_2;
    private String[] mBlood={"O","+"};
    String Willaya="Wilaya";
    String Coommun="Commune";


    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};


    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence>  commmunsCodeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("donationts","before inflate view ");

        View view = inflater.inflate(R.layout.fragment_findadonor, container, false);
        Log.d("donationts","after inflate view ");


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewd2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        spinnerWilaya = view.findViewById(R.id.spinner_find_wilaya);
        spinnerCommuns = view.findViewById(R.id.spinner_find_communs);

        Log.d("donationts","before database ");
        mRadioGroup_1 = (RadioGroup) view.findViewById(R.id.finddonor_fragment_radiobottongroup);
        mRadioGroup_2 = (RadioGroup) view.findViewById(R.id.finddonor_fragment_radiobottongroup_second);


        mRadioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blooddonor_o) {
                    mBlood[0] = "O";
                } else if (checkedId == R.id.blooddonor_a) {
                    mBlood[0] = "A";
                } else if (checkedId == R.id.blooddonor_b) {
                    mBlood[0] = "B";
                } else if (checkedId == R.id.blooddonor_ab) {
                    mBlood[0] = "AB";
                }
                mdonateurdata= dbManagerDonateur.listdonateur(String.valueOf(spinnerWilaya.getSelectedItem()),String.valueOf(spinnerCommuns.getSelectedItem()),mBlood[0]+mBlood[1]);
                mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        mRadioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blooddonor_plus) {
                    mBlood[1] = "+";
                } else if (checkedId == R.id.blooddonor_minus) {
                    mBlood[1] = "-";
                }
                mdonateurdata= dbManagerDonateur.listdonateur(String.valueOf(spinnerWilaya.getSelectedItem()),String.valueOf(spinnerCommuns.getSelectedItem()),mBlood[0]+mBlood[1]);
                mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        dbManagerDonateur = new DBManagerDonateur(getContext());
        dbManagerDonateur.open();
        Log.d("donationts","after open it ");

        if (isNetworkAvailable(getContext())>0) {
           readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });


            Log.d("donationts","there are network ");

        } else {
            Toast.makeText(this.getContext(), "Pas de connection", Toast.LENGTH_LONG).show();
            Log.d("donationts","no unternet ");

        }


        mdonateurdata= dbManagerDonateur.listdonateur(Willaya,Coommun,mBlood[0]+mBlood[1]);
        Log.d("donationts","first length : "+ mdonateurdata.size());

        mAdapter = new DonateurAdapter(getContext(), mdonateurdata);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("donationts","finish");





        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                mAdress[2] = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, getCommuns(position));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                Willaya=String.valueOf(spinnerWilaya.getSelectedItem());
                mdonateurdata= dbManagerDonateur.listdonateur(String.valueOf(spinnerWilaya.getSelectedItem()),String.valueOf(spinnerCommuns.getSelectedItem()),mBlood[0]+mBlood[1]);
                mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
                Log.d("donationts","inside spinner ");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCommuns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Coommun=String.valueOf(spinnerCommuns.getSelectedItem());
                mdonateurdata= dbManagerDonateur.listdonateur(String.valueOf(spinnerWilaya.getSelectedItem()),String.valueOf(spinnerCommuns.getSelectedItem()),mBlood[0]+mBlood[1]);
                mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Log.d("donationts","finish");

        return view;
    }


    public void readData(FireBaseCallBack fireBaseCallBack) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isNetworkAvailable(getContext())>0){dbManagerDonateur.deleteAll();}
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("_ID_firebase").exists() &&ds.child("imaged").exists() && ds.child("fullname").exists() && ds.child("adressd").exists() && ds.child("contact").exists()&& ds.child("grsanguin").exists()&& ds.child("age").exists() ;
                    if (Is_Exist) {
                        String id_firebase = ds.child("_ID_firebase").getValue(String.class);
                        String age=ds.child("age").getValue(String.class);
                        String Name = ds.child("fullname").getValue(String.class);
                        String Place = ds.child("adressd").getValue(String.class);
                        String Grsanguin = ds.child("grsanguin").getValue(String.class);
                        String Phone = ds.child("contact").getValue(String.class);
                        String ImageUrl = ds.child("imaged").getValue(String.class);
                        if (dbManagerDonateur.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManagerDonateur.update(id_firebase, Name, Place,"0","0", Phone,age,Grsanguin,ImageUrl);
                        } else {
                            dbManagerDonateur.insert(id_firebase,Name,Place,"0","0",Phone,age,Grsanguin,ImageUrl);

                        }
                    }
                }
                mdonateurdata= dbManagerDonateur.listdonateur(String.valueOf(spinnerWilaya.getSelectedItem()),String.valueOf(spinnerCommuns.getSelectedItem()),mBlood[0]+mBlood[1]);
               mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);

           mProgressDialog.dismiss();


           Log.d("donationts","new length : "+ mdonateurdata.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
