package com.blume.isomarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText email, pword, username, phone, schoolID;
    Button signup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.regEmail);
        pword = findViewById(R.id.regPassword);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        schoolID = findViewById(R.id.schoolID);
        progressBar = findViewById(R.id.progressBar3);
        signup = findViewById(R.id.regButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String u_name = username.getText().toString();
                final String e_mail = email.getText().toString();
                final String p_word = pword.getText().toString();
                String uphone = phone.getText().toString();


                final String staff_id= schoolID.getText().toString();


                if(e_mail.isEmpty()){
                    email.setError("Fill in email field");
                    email.requestFocus();
                }
                else if(p_word.isEmpty()){
                    pword.setError("Password field cannot be empty");
                    pword.requestFocus();
                }
                else if(uphone.isEmpty()){
                    phone.setError("Phone field cannot be empty");
                    phone.requestFocus();
                }

                if(!(uphone.isEmpty())){
                    if(!(uphone.length() == 10)){
                        phone.setError("Phone number must be 10 digits");
                        phone.requestFocus();
                    }
                    else {
                        final int u_phone = Integer.parseInt(uphone);
                    }
                }
                if(!(p_word.isEmpty())){
                    if(p_word.length() < 6){
                        pword.setError("Password must be at least 6 characters");
                        pword.requestFocus();
                    }

                }


                else if (!(p_word.isEmpty() && e_mail.isEmpty())){
                    progressBar.setVisibility(View.VISIBLE);

                    //Put write new user to db code here...use u_name, e_mail, p_word, u_phone, staff_id.

                }
                else {
                    Toast.makeText(RegisterActivity.this, "An error ccured, Please try again in a few...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void SignInRedirect(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);


    }
}
