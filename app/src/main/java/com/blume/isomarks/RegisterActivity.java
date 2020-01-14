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
                //progressBar.setVisibility(View.VISIBLE);
                final String u_name = username.getText().toString();
                final String e_mail = email.getText().toString();
                final String p_word = pword.getText().toString();
                String uphone = phone.getText().toString();
                String u_code = schoolID.getText().toString();
                int Validator= 1;

                if(e_mail.isEmpty()){
                    email.setError("Fill in email field");
                    email.requestFocus();
                    Validator=0;
                }
                if(p_word.isEmpty()){
                    pword.setError("Password field cannot be empty");
                    pword.requestFocus();
                    Validator=0;
                }
                if(uphone.isEmpty()){
                    phone.setError("Phone field cannot be empty");
                    phone.requestFocus();
                    Validator=0;
                }
                if(!(uphone.length() == 10)){
                        phone.setError("Phone number must be 10 digits");
                        phone.requestFocus();
                        Validator=0;
                    }

                if(p_word.length() < 6){
                        pword.setError("Password must be at least 6 characters");
                        pword.requestFocus();
                        Validator=0;
                    }

                if (Validator==1){
                    //progressBar.setVisibility(View.VISIBLE);

                    //Put write new user to db code here...use u_name, e_mail, p_word, u_phone, staff_id.

                    RegisterConnector registerConnector = new RegisterConnector(RegisterActivity.this);
                    registerConnector.execute(u_name,e_mail,p_word,uphone,u_code);
                }

            }
        });
    }

    public void SignInRedirect(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);

    }
}
