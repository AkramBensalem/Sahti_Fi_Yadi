package com.gadg.sahtifiyadi.ui.medicaments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;


public class medicament_information extends AppCompatActivity {
    private TextView MedicamenName, MedicamenPrix, MedicamentClass, num_eng, code, domination_c_in, dosage, cond, liste, pays_du_lab,
                   	date_deng_ini, date_deng_final, forme, statut, duree_de_stab, remboursement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_information);
        updateStatusBarColor("#47A34B");
        updateActionBarColorTWO("#58E15F");
        MedicamenName=(TextView) findViewById(R.id.nom_de_marque);
        MedicamenPrix=(TextView)findViewById(R.id.prix);
        MedicamentClass=(TextView)findViewById(R.id.type);
        num_eng=(TextView)findViewById(R.id.nun_eng);
        code=(TextView)findViewById(R.id.code);
        domination_c_in=(TextView)findViewById(R.id.domination_c_i);
        dosage=(TextView)findViewById(R.id.Dosage);
        cond=(TextView)findViewById(R.id.cond);
        liste=(TextView)findViewById(R.id.Liste);
        pays_du_lab=(TextView)findViewById(R.id.pays_du_laboratoir);
        date_deng_ini=(TextView)findViewById(R.id.date_deng_initail);
        date_deng_final=(TextView)findViewById(R.id.date_deng_final);
        forme=(TextView)findViewById(R.id.Forme);
        statut=(TextView)findViewById(R.id.statut);
        duree_de_stab=(TextView)findViewById(R.id.duree_de_stabilite);
        remboursement=(TextView)findViewById(R.id.remboursement);
        Intent intent=getIntent();
        MedicamenName.setText(intent.getStringExtra("name"));
        MedicamenPrix.setText( intent.getStringExtra("classe"));
        MedicamentClass.setText(intent.getStringExtra("prix"));
        num_eng.setText(intent.getStringExtra("num_eng"));
        code.setText(intent.getStringExtra("code"));
        domination_c_in.setText( intent.getStringExtra("dcin"));
        dosage.setText(intent.getStringExtra("Dosage"));
        cond.setText(intent.getStringExtra("cond"));
        liste.setText(intent.getStringExtra("liste"));
        pays_du_lab.setText( intent.getStringExtra("pays"));
        date_deng_ini.setText(intent.getStringExtra("date_ini"));
        date_deng_final.setText(intent.getStringExtra("date_final"));
        forme.setText(intent.getStringExtra("forme"));
        duree_de_stab.setText(intent.getStringExtra("duree_destsb"));
        statut.setText(intent.getStringExtra("statut"));
        remboursement.setText(intent.getStringExtra("remboursement"));

    }
    public void updateStatusBarColor(String color){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }

    }

    public void updateActionBarColorTWO(String color){
        ActionBar bar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(color));
        bar.setBackgroundDrawable(colorDrawable);
    }
}