package com.gadg.sahtifiyadi.Etablissement;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerEtablissement;
import com.gadg.sahtifiyadi.database.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._COMMUNE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._ID;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._ID_FIREBASE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._IMAGE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._NAME;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._PHONE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._PLACE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._TYPE;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns._WILAYA;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_HOSPITAL;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_LABORATOIR;
import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class LaboActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private DBManagerEtablissement dbManagerLabo;
    private RecyclerView mRecyclerView;
    private EtablissementAdapter mAdapter;
    private String mWillayaName= "Wilaya", mSearch ="";
    private Integer mLaboType = 0;
    private final DatabaseReference LaboRefrence = FirebaseDatabase.getInstance().getReference().child("laboratoirs");
    private Spinner mWillayaSpinner;
    private RadioGroup mRadioGroup;

    //
    private DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablisment);


        //
        dbHelper = new DatabaseHelper(this);


        //
        dbManagerLabo = new DBManagerEtablissement(LaboActivity.this,TABLE_NAME_LABORATOIR);


        //
        mRadioGroup = findViewById(R.id.etablissement_activity_radiobottongroup);

        mWillayaSpinner = findViewById(R.id.spinner_wilaya);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RadioButton rd = findViewById(R.id.public_user_type);
        rd.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.public_user_type){
                    mLaboType = 0;
                    getLoaderManager().restartLoader(0, null, LaboActivity.this);
                }else if(checkedId == R.id.private_user_type){
                    mLaboType = 1;
                    getLoaderManager().restartLoader(0, null, LaboActivity.this);

                }
            }
        });

        if (isNetworkAvailable(this)>0) {
            new UpdateLaboListTask().execute();
        }




        mAdapter = new EtablissementAdapter(LaboActivity.this,null,1);
        mRecyclerView.setAdapter(mAdapter);

        mWillayaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWillayaName = String.valueOf(mWillayaSpinner.getSelectedItem());
                getLoaderManager().restartLoader(0, null, LaboActivity.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             // rien se passe
            }
        });
    }


   public void readData() {
       LaboRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               dbManagerLabo.open(TABLE_NAME_LABORATOIR);
               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   boolean Is_Exist = ds.child("_ID_Firebase").exists()
                                      && ds.child("ImageUrl").exists()
                                      && ds.child("Name").exists()
                                      && ds.child("Place").exists()
                                      && ds.child("Phone").exists()
                                      && ds.child("Wilaya").exists()
                                      && ds.child("Commune").exists()
                                      && ds.child("Description").exists()
                                      && ds.child("Service").exists()
                                      && ds.child("numOrdre").exists()
                                      && ds.child("Type").exists()
                                      && ds.child("Time").exists()
                                      && ds.child("EtablissmentExist").exists();
                   if (Is_Exist) {
                       Log.d("privqtetest","Is_Exist");

                       final String id_firebase = ds.child("_ID_Firebase").getValue(String.class);
                       final String Name = ds.child("Name").getValue(String.class);
                       final String Place = ds.child("Place").getValue(String.class);
                       final String Phone = ds.child("Phone").getValue(String.class);
                       final String ImageUrl = ds.child("ImageUrl").getValue(String.class);
                       final String wilaya = ds.child("Wilaya").getValue(String.class);
                       final String commune = ds.child("Commune").getValue(String.class);
                       final String description = ds.child("Description").getValue(String.class);
                       final String service = ds.child("Service").getValue(String.class);
                       String numOrdre = ds.child("numOrdre").getValue(String.class);
                       final String type = ds.child("Type").getValue(String.class);
                       final String time = ds.child("Time").getValue(String.class);
                       boolean exist = ds.child("EtablissmentExist").getValue(boolean.class);

                       if (!exist){
                           Log.d("privqtetest","exist");
                           dbManagerLabo.deleteByFireBaseId(id_firebase);
/*
                           new AsyncTask<Void,Void,Void>(){

                               @Override
                               protected Void doInBackground(Void... voids) {
                                   dbManagerHospital.deleteByFireBaseId(id_firebase);
                                   return null;
                               }
                           };

 */

                       } else if (dbManagerLabo.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                           Log.d("privqtetest","update");
                           dbManagerLabo.update(id_firebase, Name, description,Place,wilaya,commune, Integer.parseInt(type),Phone,service,ImageUrl);

                       } else {
                           Log.d("privqtetest","insert");
                           dbManagerLabo.insert(id_firebase, Name, description,Place,wilaya,commune, Integer.parseInt(type),Phone,service,ImageUrl);

                       }
                   }
               }
               dbManagerLabo.close();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
           }
       });


   }

    @Override
    protected void onResume() {
        super.onResume();
       getLoaderManager().restartLoader(0, null, this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManagerLabo.close();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }


    public class UpdateLaboListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (isNetworkAvailable(getBaseContext())>0) {
                readData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLoaderManager().restartLoader(0, null, LaboActivity.this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
           SearchView searchView =null;
            getMenuInflater().inflate(R.menu.search_menu,menu);
            MenuItem searchItem = menu.findItem(R.id.search_item);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            if (searchItem != null){
                searchView =(SearchView) searchItem.getActionView();
            } else  Log.d("SearchTestAkram","searchItem  null ");

            if (searchView != null){
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setQueryHint("Chercher");
                searchView.setIconified(false);
                final SearchView finalSearchView = searchView;
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearch=query;
                        getLoaderManager().restartLoader(0, null, LaboActivity.this);
                        finalSearchView.clearFocus();

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mSearch=newText;
                     // getLoaderManager().restartLoader(0, null, HopitalActivity.this);

                        return true;
                    }
                });
                    searchView.clearFocus();
            }else  Log.d("SearchTestAkram","searchView  null ");

        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if(id == 0) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    final String[] hopitalColumns = {
                            _ID,
                            _ID_FIREBASE,
                            _NAME,
                            _PLACE,
                            _WILAYA,
                            _COMMUNE,
                            _PHONE,
                            _IMAGE};

                    String selection=null;
                    String[] selectionArgs= null;
                    if (mWillayaName.equals("Wilaya")){
                        selection = _TYPE + " = ? AND " + _NAME + " LIKE ?";
                        selectionArgs = new String[]{String.valueOf(mLaboType),"%"+mSearch+"%"};
                    }else {
                        selection = _TYPE + " = ? AND " + _NAME + " LIKE ? AND "+_PLACE + " LIKE ?";
                        selectionArgs = new String[]{String.valueOf(mLaboType),"%"+mSearch+"%", "%"+mWillayaName.toUpperCase()};
                    }


                    String OrderBy = _NAME ;
                    return db.query(TABLE_NAME_LABORATOIR, hopitalColumns,
                            selection, selectionArgs, null, null, OrderBy ,"20");
                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == 0)  {
            mAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == 0)  {
            mAdapter.changeCursor(null);
        }
    }
}