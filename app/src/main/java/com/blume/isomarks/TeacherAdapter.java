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
    private ArrayList<String> mEmail;
    private ArrayList<String> mClass;

    public TeacherAdapter(Context mContext, ArrayList<String> mTeacherNames, ArrayList<String> mSub1, ArrayList<String> mEmail
    ,ArrayList<String> mClass) {
        this.mContext = mContext;
        this.mTeacherNames = mTeacherNames;
        this.mSub1 = mSub1;
        this.mEmail = mEmail;
        this.mClass = mClass;
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

        holder.TeacherNam.setTitle(mTeacherNames.get(position));
        holder.Sub1.setText(mSub1.get(position));
        holder.Email.setText(mEmail.get(position));
        holder.Class.setText(mClass.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("listSize",Integer.toString(mTeacherNames.size()));

        return mTeacherNames.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        com.alespero.expandablecardview.ExpandableCardView TeacherNam;
        TextView Sub1,Email,Class;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TeacherNam = itemView.findViewById(R.id.testcard);
            Sub1 = itemView.findViewById(R.id.subject);
            Email = itemView.findViewById(R.id.email);
            Class = itemView.findViewById(R.id.Class);
        }
    }
}

