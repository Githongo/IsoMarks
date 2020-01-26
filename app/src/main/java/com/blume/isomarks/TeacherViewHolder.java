package com.blume.isomarks;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherViewHolder extends RecyclerView.ViewHolder {

    TextView TeacherName,Subject1,Subject2;


    public TeacherViewHolder(@NonNull View itemView) {
        super(itemView);
        this.TeacherName = itemView.findViewById(R.id.TeacherName);
        this.Subject1 = itemView.findViewById(R.id.Subject1Name);
        this.Subject2 = itemView.findViewById(R.id.Subject2Name);



    }
}
