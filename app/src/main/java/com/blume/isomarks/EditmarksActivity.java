package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blume.edithelpers.EditAdapter;
import com.blume.edithelpers.EditModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditmarksActivity extends AppCompatActivity {
    private ListView editLv;
    private EditAdapter editAdapter;
    public ArrayList<EditModel> editModelArrayList;
    ProgressBar progressBar;
    String studentList_url = "http://kilishi.co.ke/Android_list_students.php";
    String editResults_url = "http://kilishi.co.ke/Android_edit_results.php";
    JSONArray students;
    ArrayList<ArrayList<String>> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmarks);
        //initializing toolbar
        Toolbar myToolbar = findViewById(R.id.editmarks_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(getIntent().getExtras().getString("subjectName")+", "+getIntent().getExtras().getString("stream"));

        StringRequest studentRequest = new StringRequest(Request.Method.POST, studentList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    students = new JSONArray(response);
                    int len = students.length();
                    Toast.makeText(EditmarksActivity.this, "Showing " + Integer.toString(len)+ " Students", Toast.LENGTH_LONG).show();

                    //initializing lv
                    editLv = (ListView) findViewById(R.id.editListView);

                    editModelArrayList = populateList();
                    editAdapter = new EditAdapter(EditmarksActivity.this,editModelArrayList);
                    editLv.setAdapter(editAdapter);
                    progressBar = findViewById(R.id.editProgressBar);
                    progressBar.setVisibility(View.GONE);

                }
                catch (JSONException je){
                    Toast.makeText(EditmarksActivity.this,je.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditmarksActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("schoolCode", "100000");
                params.put("class", "1");
                params.put("streamNo", "1000005");
                params.put("subject", getIntent().getExtras().getString("subjectName"));


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(studentRequest);


    }

    //inflating toolbar with menu res
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmarks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.editmarks_menu_help){
            Toast.makeText(EditmarksActivity.this, "Ntakusort...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.editmarks_menu_save){

            StringRequest editRequest = new StringRequest(Request.Method.POST, editResults_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(EditmarksActivity.this, "Saved",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditmarksActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("examID", "001");
                    params.put("subject", getIntent().getExtras().getString("subjectName"));
                    String data = new Gson().toJson(editModelArrayList);
                    params.put("results", data);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(editRequest);


        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<EditModel> populateList() {
        ArrayList<EditModel> list = new ArrayList<>();

        int len = students.length();

        studentList = new ArrayList<>(len);

        for(int i = 0; i < len; i++){
            EditModel editModel = new EditModel();

            try {
                JSONObject studentObj = students.getJSONObject(i);
                editModel.setAdmTextValue(studentObj.getString("studentID"));
                editModel.setNameTextValue(studentObj.getString("name"));
                editModel.setEditTextValue(studentObj.getString("mark"));
                list.add(editModel);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(EditmarksActivity.this,e.toString(),Toast.LENGTH_LONG).show();

            }

        }

        return list;
    }


}
