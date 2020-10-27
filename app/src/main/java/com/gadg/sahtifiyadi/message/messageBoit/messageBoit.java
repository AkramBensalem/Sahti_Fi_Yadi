package com.gadg.sahtifiyadi.message.messageBoit;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.DBManagerMessage;
import com.gadg.sahtifiyadi.database.DatabaseHelper;
import com.gadg.sahtifiyadi.database.TablesColumnsNames;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Message_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_MESSAGES;
import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class messageBoit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public final static String TAG = messageBoit.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private messageAdapter mAdapter;
    private DBManagerMessage db;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout mlinearLayout;
    private TextView emptyText;
    private ImageView emptyImage;
  //  private Timer timer;
  //
  private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        //
        dbHelper = new DatabaseHelper(this);

        mlinearLayout=(LinearLayout) findViewById(R.id.message_boit_empty_linear);
        emptyImage = (ImageView) findViewById(R.id.message_boit_empty_image);
        emptyText = (TextView) findViewById(R.id.message_boit_empty_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage);


        mAdapter = new messageAdapter(this,null);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        db = new DBManagerMessage(getBaseContext());
        db.open();


        mRecyclerView.setAdapter(mAdapter);


    }
    public void Afficher(Boolean vide){
        if (vide){
            mlinearLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mlinearLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isNetworkAvailable(getBaseContext())>0){
            menu.getItem(0).setIcon(R.drawable.ic_baseline_sync_blue_24);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_round_sync_problem_blue);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh_message){
            if (isNetworkAvailable(getBaseContext())>0){
                invalidateOptionsMenu();
                Toast.makeText(getBaseContext(),"synchronise",Toast.LENGTH_SHORT).show();
            }else {
                    new AlertDialog.Builder(this)
                            .setTitle("Problem de connection")
                            .setMessage("Vouillez vous vérifie votre connection")
                            .setPositiveButton("J'ai compris", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        invalidateOptionsMenu();
                                    }
                            })
                            .setIcon(R.drawable.ic_round_perm_scan_wifi_24)
                            .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
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
                            RECENT_MESSAGE,
                            MESSAGE_RECENT_DATE,
                            IS_READ,
                            _IMAGE};

                    String OrderBy = "datetime("+MESSAGE_RECENT_DATE+") DESC" ;
                    Cursor cursor = db.query(TABLE_NAME_MESSAGES, hopitalColumns,
                            null, null, null, null, OrderBy,"20" );
                Afficher(cursor == null);
                return cursor;
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
