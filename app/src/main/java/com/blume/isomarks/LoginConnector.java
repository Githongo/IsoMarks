package com.blume.isomarks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;


public class LoginConnector extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    ProgressBar progressBar1;
    String UserEmail;

    LoginConnector(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        //this is the url that accesses the database
        String login_url = "http://kilishi.co.ke/login.php";
        if (type.equals("Login")){

            try {
                //This block of code attaches some POST values to the above url (urlquery)
                UserEmail = params[1];
                String Pasw = params[2];
                URL url =  new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(UserEmail,"UTF-8")
                        +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Pasw,"UTF-8");
                bufferedWriter.write(post_data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //this block retrieves the response from the server
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine())!=null){
                    result += line;
                    //this is just a log test
                    Log.d("login3",result);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(String result) {
        //the messages retrieved here is from the database (to be replaced with values we want)

        DbLoginUser(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void DbLoginUser(String result){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        if(result==null){
            alertDialog.setMessage("connection timed-out");
            alertDialog.show();
        }
        else if (result.equals("Admin")){
            Starter();
        }
        else if(result.equals("Success")){
            //add the intent to users page here
            alertDialog.setMessage(result);
            alertDialog.show();
            toHome();
        }
        else{
            //we create the dialog title before the code excutes
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }

    private void toHome() {
        Intent toHomeActivity = new Intent(context, HomeActivity.class);
        Bundle b = new Bundle();
        b.putString("email", UserEmail);
        toHomeActivity.putExtras(b);

        context.startActivity(toHomeActivity);
    }

    public void Starter(){
        Intent i = new Intent(context, landing_admin.class);
        context.startActivity(i);
    }
}
