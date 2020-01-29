package com.blume.isomarks;


import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeachersActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;

    private ArrayList<String> mTeacherNames = new ArrayList<>();
    private ArrayList<String> mSub1 = new ArrayList<>();
    private ArrayList<String> mSub2 = new ArrayList<>();

    private static final String TAG = "TeachersActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teachers);

        mRequestQueue = Volley.newRequestQueue(this);

        fetchJsonResponse();



    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recycler);
        TeacherAdapter adapter = new TeacherAdapter(this,mTeacherNames,mSub1,mSub2);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void fetchJsonResponse() {


        JsonArrayRequest req = new JsonArrayRequest("http://kilishi.co.ke/Android_list_teachers.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        mTeacherNames.add((String)jsonObject.get("Username"));
                        mSub1.add("+254"+ jsonObject.get("phoneNo"));
                        mSub2.add((String)jsonObject.get("Email"));



                    }catch (JSONException e){
                    }
                }
                initRecyclerView();



            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        mRequestQueue.add(req);
    }


}
