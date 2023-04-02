package com.example.a1;

import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OcenyViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup;
    TextView subjectNameTextView;

    public OcenyViewHolder(@NonNull View itemView) {
        super(itemView);
        radioGroup = itemView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        subjectNameTextView = itemView.findViewById(R.id.labelPrzedmiot);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        ModelOceny modelOceny = (ModelOceny) radioGroup.getTag();
        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
            if (radioButton.getId() == i) {
                modelOceny.setOcena(Integer.parseInt(radioButton.getText().toString()));
                break;
            }
        }
    }
}
