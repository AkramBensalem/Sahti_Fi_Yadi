package com.gadg.sahtifiyadi.ui.medicaments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MedicamentFragment extends Fragment {
    private static final String TAG = "MedicamFragmentramTest1";


    private MedicamentsAdapter mAdapter;
    private RequestQueue requestQueue;
    private SearchView searchView;
    public ArrayList<Medicament> mMedicamentsData ;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medicament, container, false);
        ((MainActivity) getActivity()).updateStatusBarColor("#47A34B");
        ((MainActivity) getActivity()).updateActionBarColorTWO("#58E15F");

        Log.d(TAG, "after view");
        final RecyclerView mRecyclerView = view.findViewById(R.id.medicament_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMedicamentsData = new ArrayList<Medicament>();
        searchView = view.findViewById(R.id.search_medicament);
        requestQueue= Volley.newRequestQueue(getContext());
        String url="https://my-json-server.typicode.com/arslimane/med/db";
        Log.d(TAG, "after url");

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try { Log.d(TAG,"westll");
                    JSONArray jsonArray=response.getJSONArray("data");
                    Log.d(TAG,"westll");
                    String MedicamenName;
                    String MedicamenPrix;
                    String MedicamentClass;
                    String Id;
                    String num_eng;
                    String code;
                    String domination_c_in;
                    String dosage;
                    String cond;
                    String liste;
                    String pays_du_lab;
                    String date_deng_ini;
                    String date_deng_final;
                    String forme;
                    String statut;
                    String duree_de_stab;
                    String remboursement;
                    JSONObject jsonObject;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        MedicamenName=jsonObject.getString("NOM_DE_MARQUE");
                        MedicamenPrix=jsonObject.getString("PRIX_PORTE_SUR_LA_DECISION_DENREGISTREMENT");
                        MedicamentClass=jsonObject.getString("TYPE");
                        Id=jsonObject.getString("ID");
                        num_eng=jsonObject.getString("NUM_ENREGISTREMENT");
                        code=jsonObject.getString("CODE");
                        domination_c_in=jsonObject.getString("DENOMINATION_COMMUNE_INTERNATIONALE");
                        dosage=jsonObject.getString("DOSAGE");
                        cond=jsonObject.getString("COND");
                        liste=jsonObject.getString("LISTE");
                        pays_du_lab=jsonObject.getString("PAYS_DU_LABORATOIRE_DETENTEUR_DE_LA_DECISION_DENREGISTREMENT");
                        date_deng_final=jsonObject.getString("DATE_DENREGISTREMENT_FINAL");
                        date_deng_ini=jsonObject.getString("DATE_DENREGISTREMENT_INITIAL");
                        forme=jsonObject.getString("FORME");
                        statut=jsonObject.getString("STATUT");
                        duree_de_stab=jsonObject.getString("DUREE_DE_STABILITE");
                        remboursement=jsonObject.getString("REMBOURSEMENT");
                        Log.d("medicament_test",MedicamenName+MedicamenPrix+Id+liste+code+dosage+forme+statut+domination_c_in+pays_du_lab);
                        mMedicamentsData.add(new Medicament(MedicamenName,MedicamenPrix,MedicamentClass,Id,num_eng,code,domination_c_in,dosage,cond,liste,pays_du_lab,date_deng_ini,date_deng_final,forme,statut,duree_de_stab,remboursement));
                    }
                    mAdapter = new MedicamentsAdapter(getActivity(), mMedicamentsData);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            mAdapter.filter(query);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            mAdapter.filter(newText);
                            return true;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
        Log.d(TAG, String.valueOf(mMedicamentsData.size()));


        return view;
    }
}
