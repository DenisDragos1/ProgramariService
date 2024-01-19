package com.example.proiectservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> arrayList;
    OnItemClickListener onItemClickListener;

    public NoteAdapter(Context context, ArrayList<Note> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = arrayList.get(position);

        holder.numeClient.setText(note.getNumeClient());
        holder.masina.setText(note.getMasina());
        holder.constatare.setText(note.getConstatare());
        holder.dataProgramare.setText(note.getDataProgramare());
        holder.pret.setText(String.valueOf(note.getPret()));

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(note));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numeClient, masina, constatare, dataProgramare, pret;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numeClient = itemView.findViewById(R.id.list_item_nume_client);
            masina = itemView.findViewById(R.id.list_item_masina);
            constatare = itemView.findViewById(R.id.list_item_constatare);
            dataProgramare = itemView.findViewById(R.id.list_item_data_programare);
            pret = itemView.findViewById(R.id.list_item_pret);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Note note);
    }
}
