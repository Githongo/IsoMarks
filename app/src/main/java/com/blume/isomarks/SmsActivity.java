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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SmsActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText phone;
    com.google.android.material.textfield.TextInputLayout phoneTextLayout;
    Spinner groupContactsSpinner;
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
