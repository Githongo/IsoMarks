package com.blume.isomarks;


import android.os.Bundle;
import android.util.Log;

import com.blume.isomarks.model.TeachersModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeachersActivity extends AppCompatActivity {

    RecyclerView mrecyclerView;
    TeacherAdapter mteacherAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teachers);


        mrecyclerView = findViewById(R.id.my_recycler);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mteacherAdapter = new TeacherAdapter(this,getTeachers());
        mrecyclerView.setAdapter(mteacherAdapter);

    }

    private ArrayList<TeachersModel> getTeachers(){

        ArrayList<TeachersModel> models = new ArrayList<>();

        TeachersModel teachersModel = new TeachersModel();

        teachersModel.setTeacherName("mr. Pingu");
        teachersModel.setSubject1("English");
        teachersModel.setSubject2("Mathematics");

        models.add(teachersModel);
        Log.d("teacherslist","uploaded");

        return models;

    }

}
