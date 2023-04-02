package com.example.a1;

import android.view.Display;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.app.Activity;


public class InteraktywnyAdapterTabllicy extends RecyclerView.Adapter<OcenyViewHolder>{
    private final List<ModelOceny> mListaOcen;
    private final LayoutInflater mPompka;

    public InteraktywnyAdapterTabllicy(Activity kontekst, List<ModelOceny> listaOcen){
        mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaOcen;
    }

    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mPompka.inflate(R.layout.wiersz_ocena, parent, false);
        return new OcenyViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder holder, int position) {
        ModelOceny modelOceny = mListaOcen.get(position);
        holder.subjectNameTextView.setText(modelOceny.getNazwa());
        holder.radioGroup.check(R.id.radioButton1);
        holder.radioGroup.setTag(modelOceny);
        holder.radioGroup.setOnCheckedChangeListener(holder);

    }

    @Override
    public int getItemCount() {
        return mListaOcen.size();
    }
}
