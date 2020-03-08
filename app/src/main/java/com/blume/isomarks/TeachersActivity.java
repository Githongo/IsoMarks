package com.blume.isomarks;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
    private ArrayList<String> mEmail = new ArrayList<>();
    private ArrayList<String> mClass = new ArrayList<>();
    ProgressBar mprogressBar;
    private static final String TAG = "TeachersActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teachers);
        mRequestQueue = Volley.newRequestQueue(this);
        mprogressBar = findViewById(R.id.TeacherlistProgress);
        VolleyCallback volleyCallback=new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    mTeacherNames.add((String)jsonObject.get("Username"));
                    mSub1.add((String) jsonObject.get("Subject"));
                    mEmail.add((String)jsonObject.get("Email"));
                    mClass.add("form "+jsonObject.get("Class"));
                    Log.d("volleyTest",Integer.toString(mTeacherNames.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        fetchJsonResponse(volleyCallback);



    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recycler);
        TeacherAdapter adapter = new TeacherAdapter(this,mTeacherNames,mSub1,mEmail,mClass);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void fetchJsonResponse(final VolleyCallback volleyCallback) {

        JsonArrayRequest req = new JsonArrayRequest("http://kilishi.co.ke/Android_list_teachers.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("response",response.toString());


                for (int i = 0; i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);




                    volleyCallback.onSuccess(jsonObject);

                    }catch (JSONException e){
                    }
                }

                initRecyclerView();
                mprogressBar.setVisibility(View.GONE);

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

    public interface VolleyCallback{

        void onSuccess(JSONObject jsonObject);
    }

}
