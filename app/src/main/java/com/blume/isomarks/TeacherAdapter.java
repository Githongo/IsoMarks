package com.blume.isomarks;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blume.isomarks.model.TeachersModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {

    Context c;
    ArrayList<TeachersModel> models;

    public TeacherAdapter(Context c, ArrayList<TeachersModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_card_layout,null);

        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int i) {

        holder.TeacherName.setText(models.get(i).getTeacherName());
        holder.Subject1.setText(models.get(i).getSubject1());
        holder.Subject2.setText(models.get(i).getSubject2());


    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
