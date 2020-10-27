package com.gadg.sahtifiyadi.ui.medicaments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gadg.sahtifiyadi.R;

import java.util.ArrayList;

public class MedicamentsAdapter extends RecyclerView.Adapter<MedicamentsAdapter.MedicamentViewHolder>  {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Medicament> mMedicamentData;
    public ArrayList<Medicament> mMedicamentDataArray= new ArrayList<Medicament>();
    public Context mContext;
    private  Medicament currentMedicament;

    MedicamentsAdapter(Context context, ArrayList<Medicament> medicamntData) {
        this.mMedicamentData = medicamntData;
        this.mContext = context;
        this.mMedicamentDataArray.addAll(mMedicamentData);
//Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.medicament);
        if(drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    public void filter(String text) {
        if(text.isEmpty()){
            mMedicamentData.clear();
            mMedicamentData.addAll(mMedicamentDataArray);
        } else{
            ArrayList<Medicament> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Medicament item: mMedicamentDataArray){
                //match by name or phone
                if(item.getMedicamenName().toLowerCase().contains(text) ||
                        item.getMedicamenPrix().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mMedicamentData.clear();
            mMedicamentData.addAll(result);
        }
        notifyDataSetChanged();
    }

    @Override
    public MedicamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.medicament_list_item,parent,false);
        return new MedicamentViewHolder(mContext,view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(MedicamentViewHolder holder, int position) {

        //Get the current sport
        currentMedicament = mMedicamentData.get(position);

        //Bind the data to the views
        holder.bindTo(currentMedicament);


    }


    @Override
    public int getItemCount() {
        return mMedicamentData.size();
    }

    static class MedicamentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        private TextView mNameMedicamentTextView;
        private TextView mPrixMedicamentTextView;
        private TextView mClassMedicamentTextView;
        private ImageView mMedicamentImage;
        private Context mCont;
        private Medicament mCurrentMedicament;
        private GradientDrawable mGradientDrawable;

        MedicamentViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mNameMedicamentTextView = (TextView)itemView.findViewById(R.id.medicament_name);
            mClassMedicamentTextView = (TextView)itemView.findViewById(R.id.medicament_class);
            mPrixMedicamentTextView=(TextView)itemView.findViewById(R.id.medicament_prix);
            mMedicamentImage = (ImageView)itemView.findViewById(R.id.medicament_image);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Medicament currentMedicament){
            //Populate the textviews with data
            mNameMedicamentTextView.setText(currentMedicament.getMedicamenName());
            mClassMedicamentTextView.setText(currentMedicament.getMedicamentClass());
            mPrixMedicamentTextView.setText(currentMedicament.getMedicamenPrix());


            //Get the current sport
            mCurrentMedicament = currentMedicament;

            Glide.with(mCont).load(R.drawable.medicament_two).placeholder(mGradientDrawable).into(mMedicamentImage);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mCont, medicament_information.class);
            intent.putExtra("name",mCurrentMedicament.getMedicamenName());
            intent.putExtra("Id",mCurrentMedicament.getId());
            intent.putExtra("num_eng",mCurrentMedicament.getNum_eng());
            intent.putExtra("code",mCurrentMedicament.getCode());
            intent.putExtra("dcin",mCurrentMedicament.getDomination_c_in());
            intent.putExtra("forme",mCurrentMedicament.getForme());
            intent.putExtra("Dosage",mCurrentMedicament.getDosage());
            intent.putExtra("cond",mCurrentMedicament.getCond());
            intent.putExtra("liste",mCurrentMedicament.getListe());
            intent.putExtra("pays",mCurrentMedicament.getPays_du_lab());
            intent.putExtra("date_ini",mCurrentMedicament.getDate_deng_ini());
            intent.putExtra("date_final",mCurrentMedicament.getDate_deng_final());
            intent.putExtra("classe",mCurrentMedicament.getMedicamentClass());
            intent.putExtra("prix",mCurrentMedicament.getMedicamenPrix());
            intent.putExtra("statut",mCurrentMedicament.getStatut());
            intent.putExtra("remboursement",mCurrentMedicament.getRemboursement());
            intent.putExtra("duree_destsb",mCurrentMedicament.getDuree_de_stab());
            mCont.startActivity(intent);



        }
    }
}
