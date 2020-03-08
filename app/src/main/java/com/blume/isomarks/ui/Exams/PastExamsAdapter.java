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

public class PastExamsAdapter extends RecyclerView.Adapter<PastExamsAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<String> mExamName;
    private ArrayList<String> mFrom;
    private ArrayList<String> mTo;

    public PastExamsAdapter(Context mContext, ArrayList<String> mExamName, ArrayList<String> mFrom, ArrayList<String> mTo) {
        this.mContext = mContext;
        this.mExamName = mExamName;
        this.mFrom = mFrom;
        this.mTo = mTo;
    }

    @NonNull
    @Override
    public PastExamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_exams_holder,parent,false);
        PastExamsAdapter.ViewHolder holder = new PastExamsAdapter.ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull PastExamsAdapter.ViewHolder holder, int position) {
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

            ExamName = itemView.findViewById(R.id.examTitle);
            From = itemView.findViewById(R.id.fromP);
            To = itemView.findViewById(R.id.toP);
        }






    }
}
