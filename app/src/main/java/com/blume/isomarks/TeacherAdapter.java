package com.blume.isomarks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{

    Context mContext;
    private ArrayList<String> mTeacherNames;
    private ArrayList<String> mSub1;
    private ArrayList<String> mSub2;

    public TeacherAdapter(Context mContext, ArrayList<String> mTeacherNames, ArrayList<String> mSub1, ArrayList<String> mSub2) {
        this.mContext = mContext;
        this.mTeacherNames = mTeacherNames;
        this.mSub1 = mSub1;
        this.mSub2 = mSub2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_card_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("secondtry","onBindViewHolder called");

        holder.TeacherNam.setText(mTeacherNames.get(position));
        holder.Sub1.setText(mSub1.get(position));
        holder.Sub2.setText(mSub2.get(position));

    }

    @Override
    public int getItemCount() {
        Log.d("listSize",Integer.toString(mTeacherNames.size()));

        return mTeacherNames.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView TeacherNam,Sub1,Sub2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TeacherNam = itemView.findViewById(R.id.TeacherName);
            Sub1 = itemView.findViewById(R.id.Subject1Name);
            Sub2 = itemView.findViewById(R.id.Subject2Name);
        }
    }
}

