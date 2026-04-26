package com.example.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.app.R;
import com.example.app.classes.Etudiant;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {

    private List<Etudiant> etudiants;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Etudiant etudiant);
    }

    public EtudiantAdapter(List<Etudiant> etudiants, OnItemClickListener listener) {
        this.etudiants = etudiants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etudiant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant etudiant = etudiants.get(position);
        holder.tvId.setText(String.valueOf(etudiant.getId()));
        holder.tvNom.setText(etudiant.getNom());
        holder.tvPrenom.setText(etudiant.getPrenom());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(etudiant));
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public void updateList(List<Etudiant> newList) {
        this.etudiants = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvNom, tvPrenom;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvNom = itemView.findViewById(R.id.tvNom);
            tvPrenom = itemView.findViewById(R.id.tvPrenom);
        }
    }
}