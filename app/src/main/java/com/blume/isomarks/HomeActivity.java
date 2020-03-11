package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    TextView greetingText;
    com.google.android.material.card.MaterialCardView resultsCard, smsCard, classlistCard, groupMakerCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initializing toolbar (Appbar)
        Toolbar myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("IsoMarks");

        //greeting
        greetingText = findViewById(R.id.greetingText);
        getGreeting();

        //handling cards
        resultsCard = findViewById(R.id.resultsOption);
        smsCard = findViewById(R.id.smsOption);
        classlistCard = findViewById(R.id.classListOption);
        groupMakerCard = findViewById(R.id.groupMakerOption);

        resultsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSubjects = new Intent(HomeActivity.this, SubjectActivity.class);
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
            greetingText.setText("Good Morning "+getIntent().getExtras().getString("Name"));
        }
        else if(timeOfDay <16){
            greetingText.setText("Good Afternoon "+getIntent().getExtras().getString("Name"));
        }
        else {
            greetingText.setText("Good Evening "+getIntent().getExtras().getString("Name"));
        }
    }
}
