package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditmarksActivity extends AppCompatActivity {
    private ListView editLv;
    private EditAdapter editAdapter;
    public ArrayList<EditModel> editModelArrayList;
    String studentList_url = "http://kilishi.co.ke/Android_list_students.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmarks);
        //initializing toolbar
        Toolbar myToolbar = findViewById(R.id.editmarks_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(getIntent().getExtras().getString("subjectName")+", "+getIntent().getExtras().getString("stream"));
        loadStudents();

        //initializing lv
        editLv = (ListView) findViewById(R.id.editListView);

        editModelArrayList = populateList();
        editAdapter = new EditAdapter(this,editModelArrayList);
        editLv.setAdapter(editAdapter);



    }

    private void loadStudents() {
        StringRequest studentRequest = new StringRequest(Request.Method.POST, studentList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray students = new JSONArray(response);
                    JSONObject studentsObj = students.getJSONObject(0);
                    Toast.makeText(EditmarksActivity.this,studentsObj.getString("name"),Toast.LENGTH_LONG).show();
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
                params.put("stream", "YALE");
                params.put("class", "3");

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

        if (itemId == R.id.editmarks_menu_sort_a_to_z){
            Toast.makeText(EditmarksActivity.this, "Ntakusort...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.editmarks_menu_sort_by_admno){
            Toast.makeText(EditmarksActivity.this, "Sorted...", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.editmarks_menu_save){
            String Mark = EditAdapter.editModelArrayList.get(18).getEditTextValue();
            Toast.makeText(EditmarksActivity.this, Mark, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<EditModel> populateList() {
        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(String.valueOf(i));
            editModel.setAdmTextValue("1234"+ i);
            editModel.setNameTextValue("anonymous, John Doe "+ i);
            list.add(editModel);
        }

        return list;
    }


}
