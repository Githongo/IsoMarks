package com.blume.isomarks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class RegisterConnector extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;

    RegisterConnector(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://kilishi.co.ke/Android_reg.php";


            try {
                //This block of code attaches some POST values to the above url (urlquery)u_name,e_mail,p_word,uphone,u_code
                String UserEmail = params[1];
                String Pasw = params[2];
                String Username = params[0];
                String Userphone = params[3];
                String UserCode = params[4];
                URL url =  new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(Username,"UTF-8")
                        +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Pasw,"UTF-8")
                        +"&"+URLEncoder.encode("user_mail","UTF-8")+"="+URLEncoder.encode(UserEmail,"UTF-8")
                        +"&"+URLEncoder.encode("user_phone","UTF-8")+"="+URLEncoder.encode(Userphone,"UTF-8")
                        +"&"+URLEncoder.encode("user_code","UTF-8")+"="+URLEncoder.encode(UserCode,"UTF-8");
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
        return null;
    }

    @Override
    protected void onPreExecute() {
        //we create the dialog title before the code excutes
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Register Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //the messages retrieved here is from the database (to be replaced with values we want)
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
