package com.blume.subjectRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blume.isomarks.EditmarksActivity;
import com.blume.isomarks.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView subjectId;
    public TextView subjectName;
    public TextView stream;
    public TextView examType;
    public TextView status;

    public SubjectViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        subjectId = (TextView) itemView.findViewById(R.id.subjectId);
        subjectName = (TextView) itemView.findViewById(R.id.subjectName);
        stream = (TextView) itemView.findViewById(R.id.stream);
        examType = (TextView) itemView.findViewById(R.id.examType);
        status = (TextView) itemView.findViewById(R.id.status);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), EditmarksActivity.class);
        Bundle b = new Bundle();
        b.putString("subjectId", subjectId.getText().toString());
        b.putString("subjectName", subjectName.getText().toString());
        b.putString("stream", stream.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
