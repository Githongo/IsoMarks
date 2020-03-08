package com.blume.isomarks.ui.Exams;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blume.isomarks.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<String> mExamName;
    private ArrayList<String> mFrom;
    private ArrayList<String> mTo;

    public ExamsAdapter(Context mContext, ArrayList<String> mExamName, ArrayList<String> mFrom, ArrayList<String> mTo) {
        this.mContext = mContext;
        this.mExamName = mExamName;
        this.mFrom = mFrom;
        this.mTo = mTo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_card_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("secondtry","onBindViewHolder called");

        holder.ExamName.setText(mExamName.get(position));
        holder.From.setText(mFrom.get(position));
        holder.To.setText(mTo.get(position));
    }

    @Override
    public int getItemCount() {
        return mExamName.size();
    }



    //ViewHolder
    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView From,To,ExamName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ExamName = itemView.findViewById(R.id.ExamName);
            From = itemView.findViewById(R.id.From);
            To = itemView.findViewById(R.id.To);
        }






    }
}
