package com.blume.subjectRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blume.isomarks.R;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectViewHolders> {
    private List<Subject> itemList;
    private Context context;

    public SubjectAdapter(List<Subject> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        SubjectViewHolders rcv = new SubjectViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolders holder, int position) {
        holder.subjectId.setText(itemList.get(position).getSubjectId());
        holder.subjectName.setText(itemList.get(position).getSubjectName());
        holder.stream.setText("Class: "+itemList.get(position).getStream());
        holder.examType.setText("Exam: "+itemList.get(position).getExamType());
        holder.status.setText("Status: "+itemList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
