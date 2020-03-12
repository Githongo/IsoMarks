package com.blume.isomarks;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blume.edithelpers.EditAdapter;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmsActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText phone, message;
    com.google.android.material.textfield.TextInputLayout phoneTextLayout;
    Spinner groupContactsSpinner;
    Button sendBtn;
    String sendSms_url = "https://isomarks.co.ke/sendsms";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //initializing toolbar (Appbar)
        Toolbar myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Send Message");

        //initializing spinner
        groupContactsSpinner = findViewById(R.id.groupContactsSpinner);
        ArrayList<String> groupsArray = new ArrayList<String>();

        groupsArray.add("Form 1 D");
        groupsArray.add("Form 2 A");
        groupsArray.add("Form 3 E");
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,groupsArray);
        groupContactsSpinner.setAdapter(adp);

        phone = findViewById(R.id.phone);
        phoneTextLayout = findViewById(R.id.phoneHolder);


        ChipGroup chipGroup = (ChipGroup) findViewById(R.id.contactChipGroup);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
                switch(checkedId) {
                    case R.id.singleContactChip:
                            phoneTextLayout.setVisibility(View.VISIBLE);
                            groupContactsSpinner.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.groupContactsChip:
                            phoneTextLayout.setVisibility(View.INVISIBLE);
                            groupContactsSpinner.setVisibility(View.VISIBLE);
                                //Set listener Called when the item is selected in spinner
                                groupContactsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                {
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long arg3)
                                    {
                                        String city = "Group Contact" + parent.getItemAtPosition(position).toString();
                                        Toast.makeText(parent.getContext(), city, Toast.LENGTH_LONG).show();

                                    }

                                    public void onNothingSelected(AdapterView<?> arg0)
                                    {
                                        Toast.makeText(SmsActivity.this, "Please Select a Contact Group!", Toast.LENGTH_LONG).show();
                                    }
                                });
                        break;

                    default:
                        phoneTextLayout.setVisibility(View.INVISIBLE);
                        groupContactsSpinner.setVisibility(View.INVISIBLE);
                        Toast.makeText(SmsActivity.this, "Please Select Single or Group Contact!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //send sms
        phone = findViewById(R.id.phone);
        message = findViewById(R.id.messageEditText);
        sendBtn = findViewById(R.id.sendButton);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int validator=1;
                String to = phone.getText().toString();
                String sms = message.getText().toString();
                if (!(to.length() == 10)) {
                    phone.setError("Phone number must be 10 digits");
                    phone.requestFocus();
                    validator = 0;
                }
                if (sms.isEmpty()) {
                    message.setError("Message field cannot be empty");
                    message.requestFocus();
                    validator = 0;
                }
                if(validator == 1){
                    StringRequest smsRequest = new StringRequest(Request.Method.POST, sendSms_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                               /* JSONArray smsResponse = new JSONArray(response);
                                JSONObject responseObj = smsResponse.getJSONObject(1);
                                String Status = responseObj.getString("status");*/

                                Toast.makeText(SmsActivity.this, "Sent", Toast.LENGTH_LONG).show();
                            } catch (Exception je) {
                                Toast.makeText(SmsActivity.this, je.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SmsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("singleContact", phone.getText().toString());
                            params.put("message", message.getText().toString());

                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(SmsActivity.this);
                    requestQueue.add(smsRequest);
                }
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
            Toast.makeText(SmsActivity.this, "Google it...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.actionbar_settings){
            Toast.makeText(SmsActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            //insert settings code here...
        }
        if (itemId == R.id.actionbar_logout){
            //Insert Logout code here...if any ;)!
        }

        return super.onOptionsItemSelected(item);
    }



}
