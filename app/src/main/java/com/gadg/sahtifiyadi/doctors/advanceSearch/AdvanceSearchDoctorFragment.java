package com.gadg.sahtifiyadi.doctors.advanceSearch;


import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerDoctor;
import com.gadg.sahtifiyadi.database.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getLastSpeciality;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Doctor_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_DOCTORS;
import static com.gadg.sahtifiyadi.utilities.tools.getCommuns;
import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TAG = AdvanceSearchDoctorFragment.class.getSimpleName();

    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
    private RecyclerView mRecyclerView;
    private DBManagerDoctor dbManager;
    private DoctorsAdapter mAdapter;
    private Spinner spinnerWilaya, spinnerCommuns;
    private TextView nbrItem;
    private ArrayAdapter<CharSequence>  commmunsCodeAdapter;
    private LinearLayoutManager layoutManager;
    private String mWilayaName ="Blida", commune;
    private String mSpeciality, mSearch="";

    //
    private DatabaseHelper dbHelper;




    public AdvanceSearchDoctorFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);

        //
        dbHelper = new DatabaseHelper(getContext());

        //
        dbManager = new DBManagerDoctor(getActivity());

        mRecyclerView = view.findViewById(R.id.doctor_recycler_advanced_search);
        spinnerWilaya = view.findViewById(R.id.spinner_doctor_wilaya);
        spinnerCommuns = view.findViewById(R.id.spinner_doctor_communs);
        layoutManager = new LinearLayoutManager(getActivity());
        mSpeciality = getLastSpeciality(getContext());
        dbManager.open();
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DoctorsAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);
        if (isNetworkAvailable(getContext())>0) {
            new UpdateDoctorListTask().execute();
        }

        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   spinnerCommuns.setVisibility(View.VISIBLE);
                   mWilayaName = String.valueOf(spinnerWilaya.getSelectedItem());
                   commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, getCommuns(position));
                   commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   spinnerCommuns.setAdapter(commmunsCodeAdapter);
                    loadData();
                   //

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCommuns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void readData() {
        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    boolean Is_Exist =ds.child("_ID_Firebase").exists()
                                      && ds.child("ImageUrl").exists()
                                      && ds.child("Name").exists()
                                      && ds.child("Place").exists()
                                      && ds.child("Phone").exists()
                                      && ds.child("Speciality").exists()
                                      && ds.child("Wilaya").exists()
                                      && ds.child("Commune").exists()
                                      && ds.child("Time").exists()
                                      && ds.child("Service").exists()
                                      && ds.child("DoctorExist").exists()
                                      && ds.child("Type").exists();

                    if (Is_Exist) {

                        final String id_firebase = ds.child("_ID_Firebase").getValue(String.class);
                        final String Name = ds.child("Name").getValue(String.class);
                        final String Place = ds.child("Place").getValue(String.class);
                        final String Phone = ds.child("Phone").getValue(String.class);
                        final String Spec = ds.child("Speciality").getValue(String.class);
                        final String type = ds.child("Type").getValue(String.class);
                        final String ImageUrl = ds.child("ImageUrl").getValue(String.class);
                        final String willaya = ds.child("Wilaya").getValue(String.class);
                        final String commune = ds.child("Commune").getValue(String.class);
                        final String time = ds.child("Time").getValue(String.class);
                        final String services = ds.child("Service").getValue(String.class);
                        boolean exist = ds.child("DoctorExist").getValue(boolean.class);
                        if (!exist){
                            Log.d("AdvancedAkramTest",Name +"  must deleted");
                         /*   new AsyncTask<Void,Void,Void>(){

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.deleteByFireBaseId(id_firebase);
                                    return null;
                                }
                            }.execute();

                          */

                        } else if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            Log.d("AdvancedAkramTest",Name +" exist in db");
                            dbManager.update(id_firebase, Name, Place,willaya,commune, Phone, Spec,type,services,time,ImageUrl);
                            /*new AsyncTask<Void,Void,Void>(){

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.update(id_firebase, Name, Place,willaya,commune, Phone, Spec,type,services,time,ImageUrl);
                                    return null;
                                }
                            };

                             */

                        } else {
                            Log.d("AdvancedAkramTest",Name +" doesn't in db");
                            dbManager.insert(id_firebase, Name, Place,willaya,commune, Phone, Spec,type,services,time,ImageUrl);
                           /* new AsyncTask<Void,Void,Void>(){

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.insert(id_firebase, Name, Place,willaya,commune, Phone, Spec,type,services,time,ImageUrl);
                                    return null;
                                }
                            };

                            */

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if(id == 1) {
            loader = new CursorLoader(getContext()) {
                @Override
                public Cursor loadInBackground() {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    final String[] doctorColumns = {
                            _ID,
                            _ID_FIREBASE,
                            _NAME,
                            _PLACE,
                            _WILAYA,
                            _COMMUNE,
                            _SPECIALITY,
                            _IMAGE};

                    String selection=null;
                    String[] selectionArgs= null;
                    String commune = String.valueOf(spinnerCommuns.getSelectedItem());
                    if (mWilayaName.equals("Wilaya")){
                        selection = _SPECIALITY +" = ? AND " + _NAME + " LIKE ?";
                        selectionArgs = new String[]{mSpeciality,"%"+mSearch+"%"};
                    }else {
                        if (commune.equals("Commune")){
                            selection = _SPECIALITY + " = ? AND " + _NAME + " LIKE ? AND " + _PLACE + " LIKE ?";
                            selectionArgs = new String[]{mSpeciality, "%" + mSearch + "%", "%" + mWilayaName.toUpperCase()};

                        }else {
                            selection = _SPECIALITY + " = ? AND " + _NAME + " LIKE ? AND " + _PLACE + " LIKE ?";
                            selectionArgs = new String[]{mSpeciality, "%" + mSearch + "%", "%" + commune + "%" + mWilayaName.toUpperCase()};
                        }
                    }


                    String OrderBy = _NAME ;
                    return  db.query(TABLE_NAME_DOCTORS, doctorColumns,
                            selection, selectionArgs, null, null, OrderBy);
                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == 1)  {
            mAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == 1)  {
            mAdapter.changeCursor(null);
        }
    }


    public class UpdateDoctorListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
           if (isNetworkAvailable(getContext())>0) {
                readData();
        }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadData();
          //  dbManager.close();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            mSpeciality = getLastSpeciality(getContext());
            loadData();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        SearchView searchView =null;
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null){
            searchView =(SearchView) searchItem.getActionView();
        } else  Log.d("SearchTestAkram","searchItem  null ");

        if (searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Chercher");
           searchView.setIconified(false);
            final SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearch = query;
                    loadData();
                    finalSearchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mSearch = newText;
                   // loadData();
                    return true;
                }
            });
            try {
                searchView.clearFocus();

            }catch (Exception e) {
                Log.d("SearchTestAkram","searchView  error : "+e.getMessage());
            }
        }else  Log.d("SearchTestAkram","searchView  null ");
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String[] doctorColumns = {
                _ID,
                _ID_FIREBASE,
                _NAME,
                _PLACE,
                _WILAYA,
                _COMMUNE,
                _SPECIALITY,
                _IMAGE};

        String selection=null;
        String[] selectionArgs= null;
        String commune = String.valueOf(spinnerCommuns.getSelectedItem());
        if (mWilayaName.equals("Wilaya")){
			          Log.d("ayenTest", "rani f wilaya 1");
                        selection = _SPECIALITY +" = ? AND " + _NAME + " LIKE ?";
                        selectionArgs = new String[]{mSpeciality,"%"+mSearch+"%"};
                    }else {
                        if (commune.equals("Commune")){
                            Log.d("ayenTest", "Commune 1 :"+ commune);

                            selection = _SPECIALITY + " = ? AND " + _NAME + " LIKE ? AND " + _PLACE + " LIKE ?";
                            selectionArgs = new String[]{mSpeciality, "%" + mSearch + "%", "%" + mWilayaName.toUpperCase()};

                        }else {
										          Log.d("ayenTest", "rani f commune 2");
                            Log.d("ayenTest", "Commune 2 :"+ commune);

                            selection = _SPECIALITY + " = ? AND " + _NAME + " LIKE ? AND " + _PLACE + " LIKE ?";
                            selectionArgs = new String[]{mSpeciality, "%" + mSearch + "%", "%" + commune + "%" + mWilayaName.toUpperCase()};
                        }
                    }

        String OrderBy = _NAME ;
        final Cursor doctorCursor = db.query(TABLE_NAME_DOCTORS, doctorColumns,
                selection, selectionArgs, null, null, OrderBy);
        mAdapter.changeCursor(doctorCursor);

    }
	
	
}