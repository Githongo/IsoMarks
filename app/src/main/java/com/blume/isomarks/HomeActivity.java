package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    TextView greetingText;
    com.google.android.material.card.MaterialCardView resultsCard, smsCard, classlistCard, groupMakerCard;
    String userInfo_url = "http://kilishi.co.ke/Android_get_user.php";
    JSONArray user;
    String userName, teacherID;
    String[] userNameArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initializing toolbar (Appbar)
        Toolbar myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("IsoMarks");

        //Volley request
        StringRequest userRequest = new StringRequest(Request.Method.POST, userInfo_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    user = new JSONArray(response);
                    JSONObject userObj = user.getJSONObject(0);
                    userName = userObj.getString("name");
                    userNameArr = userName.trim().split("\\s+");
                    teacherID = userObj.getString("teacherID");

                    getGreeting();
                }
                catch (JSONException je){
                    Toast.makeText(HomeActivity.this,je.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", getIntent().getExtras().getString("email"));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(userRequest);

        //greeting
        greetingText = findViewById(R.id.greetingText);


        //handling cards
        resultsCard = findViewById(R.id.resultsOption);
        smsCard = findViewById(R.id.smsOption);
        classlistCard = findViewById(R.id.classListOption);
        groupMakerCard = findViewById(R.id.groupMakerOption);

        resultsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSubjects = new Intent(HomeActivity.this, SubjectActivity.class);
                Bundle b = new Bundle();
                b.putString("userName", userName);
                b.putString("teacherID", teacherID);
                toSubjects.putExtras(b);

                startActivity(toSubjects);
            }
        });

        smsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSMS = new Intent(HomeActivity.this, SmsActivity.class);
                startActivity(toSMS);
            }
        });
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
            Toast.makeText(HomeActivity.this, "Google it...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.actionbar_settings){
            Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            //insert settings code here...
        }
        if (itemId == R.id.actionbar_logout){
            //Insert Logout code here...if any ;)!
        }

        return super.onOptionsItemSelected(item);
    }

    private void getGreeting(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay < 12){
            greetingText.setText("Good Morning "+userNameArr[0]);
        }
        else if(timeOfDay <16){
            greetingText.setText("Good Afternoon "+userNameArr[0]);
        }
        else {
            greetingText.setText("Good Evening "+userNameArr[0]);
        }
    }
}
