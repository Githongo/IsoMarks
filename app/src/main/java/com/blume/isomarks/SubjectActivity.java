package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blume.subjectRecyclerView.Subject;
import com.blume.subjectRecyclerView.SubjectAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectActivity extends AppCompatActivity {

    private RecyclerView mSubjectRecyclerView;
    private RecyclerView.Adapter mSubjectAdapter;
    private RecyclerView.LayoutManager mSubjectLayoutManager;
    JSONArray subjects;
    String subjectInfo_url = "http://kilishi.co.ke/Android_get_subjects.php";
    ArrayList<ArrayList<String>> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        //initializing toolbar (Appbar)
        Toolbar myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Subjects");

        //volley Request
        StringRequest subjectRequest = new StringRequest(Request.Method.POST, subjectInfo_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    subjects = new JSONArray(response);

                    //finding recycler view, setting attr. and creating helper class objects
                    mSubjectRecyclerView = findViewById(R.id.subjectRecyclerView);
                    mSubjectRecyclerView.setNestedScrollingEnabled(false);
                    mSubjectRecyclerView.setHasFixedSize(true);
                    mSubjectLayoutManager = new LinearLayoutManager(SubjectActivity.this);
                    mSubjectRecyclerView.setLayoutManager(mSubjectLayoutManager);
                    mSubjectAdapter = new SubjectAdapter(getDataSetSubject(), SubjectActivity.this);
                    mSubjectRecyclerView.setAdapter(mSubjectAdapter);

                }
                catch (JSONException je){
                    Toast.makeText(SubjectActivity.this,je.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SubjectActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("teacherID", getIntent().getExtras().getString("teacherID"));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(subjectRequest);


    }


    //inflating toolbar(appbar) with menu res
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    //handling options selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.actionbar_help){
            Toast.makeText(SubjectActivity.this, "Google it...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.actionbar_settings){
            Toast.makeText(SubjectActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            //insert settings code here...
        }
        if (itemId == R.id.actionbar_logout){
            //Insert Logout code here...if any ;)!
        }

        return super.onOptionsItemSelected(item);
    }

    Subject subobj = new Subject("1234", "Mathematics", "4 D", "Opener", "Editable");
    Subject subobj2 = new Subject("3456", "English", "4 S", "End Term", "Editable");

    ArrayList resultsSubjects = new ArrayList<Subject>();
    List<Subject> getDataSetSubject() {
        return getResultsSubjects();
    }

    public ArrayList getResultsSubjects() {
        int len = subjects.length();
        subjectList = new ArrayList<>(len);

        String StreamName, SubjectName;

        for(int i = 0; i < len; i++){

            try {
                JSONObject subjectsListObj = subjects.getJSONObject(i);
                StreamName = subjectsListObj.getString("streamClass")+ " "+subjectsListObj.getString("streamName");
                SubjectName = subjectsListObj.getString("subjectName");
                Subject subjectObj = new Subject("1234", SubjectName, StreamName, "End Term", "Editable");

                resultsSubjects.add(subjectObj);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SubjectActivity.this,e.toString(),Toast.LENGTH_LONG).show();

            }

        }
        //resultsSubjects.add(subobj);
        //resultsSubjects.add(subobj2);
        return resultsSubjects;
    }

}
