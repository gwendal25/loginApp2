package com.example.loginapp2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomCashAdapter extends RecyclerView.Adapter<CustomCashAdapter.viewHolder> {

    private Context context;
    private ArrayList depense_id, depense_date, depense_valeur, depense_categorie, depense_desc;

    CustomCashAdapter(Context context, ArrayList depense_id, ArrayList depense_date, ArrayList depense_valeur, ArrayList depense_categorie, ArrayList depense_desc){
        this.context = context;
        this.depense_id = depense_id;
        this.depense_date = depense_date;
        this.depense_valeur = depense_valeur;
        this.depense_categorie = depense_categorie;
        this.depense_desc = depense_desc;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.depense_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.id_text.setText(String.valueOf(depense_id.get(position)));
        holder.date_text.setText(String.valueOf(depense_date.get(position)));
        holder.valeur_text.setText(String.valueOf(depense_valeur.get(position)));
        holder.categorie_text.setText(String.valueOf(depense_categorie.get(position)));
        holder.desc_text.setText(String.valueOf(depense_desc.get(position)));
    }

    @Override
    public int getItemCount() {
        return depense_id.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView id_text, date_text, valeur_text, categorie_text, desc_text;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id_text = itemView.findViewById(R.id.depense_id);
            date_text = itemView.findViewById(R.id.depense_date);
            valeur_text = itemView.findViewById(R.id.depense_valeur);
            categorie_text = itemView.findViewById(R.id.depense_categorie);
            desc_text = itemView.findViewById(R.id.depense_desc);
        }
    }
}
