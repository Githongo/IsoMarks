package com.blume.isomarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText email, pword;
    Button signin;
    TextView pwordreset, signup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Email);
        pword = findViewById(R.id.password);
        signin = findViewById(R.id.login);
        signup = findViewById(R.id.register);
        pwordreset = findViewById(R.id.resetPassword);
        progressBar = findViewById(R.id.progressBar1);

        if(1 == 0){
            Intent toHome = new Intent(MainActivity.this, HomeActivity.class);
            Bundle b = new Bundle();
            b.putString("Name", "John");
            toHome.putExtras(b);

            startActivity(toHome);
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail = email.getText().toString();
                String p_word = pword.getText().toString();

                if(e_mail.isEmpty()){
                    email.setError("Fill in email field");
                    email.requestFocus();
                }
                else if(p_word.isEmpty()){
                    pword.setError("Password field is empty");
                    pword.requestFocus();
                }
                else if(p_word.isEmpty() && e_mail.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!!", Toast.LENGTH_SHORT).show();
                }

                else if (!(p_word.isEmpty() && e_mail.isEmpty())){
                    progressBar.setVisibility(View.VISIBLE);
                    String emailtx = email.getText().toString();
                    String passwordtx = pword.getText().toString();
                    String type = "Login";

                    //once the user clicks sign in an instance of class doinbackgroung is created and initialized with the login details
                    LoginConnector loginConnector = new LoginConnector(MainActivity.this);
                    loginConnector.execute(type,emailtx,passwordtx);

                }
                else {
                    Toast.makeText(MainActivity.this, "An error occurred, Please try again in a few minutes...", Toast.LENGTH_LONG).show();
                }

                String emailtx = email.getText().toString();
                String passwordtx = pword.getText().toString();
                String type = "Login";

                //once the user clicks sign in an instance of class doinbackgroung is created and initialized with the login details
                LoginConnector loginConnector = new LoginConnector(MainActivity.this);
                loginConnector.execute(type,emailtx,passwordtx);
                /*Intent i = new Intent(MainActivity.this, landing_admin.class);
                startActivity(i);*/

            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    /*public void Starter(){
        Intent i = new Intent(MainActivity.this, landing_admin.class);
        startActivity(i);
    }*/
}
