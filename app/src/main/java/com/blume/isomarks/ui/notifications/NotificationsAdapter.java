package com.blume.isomarks.ui.notifications;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blume.isomarks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<String> mteacherName;
    private ArrayList<String> mEmail;
    LinearLayout mEmptylist;

    public NotificationsAdapter(Context mContext, ArrayList<String> mExamName, ArrayList<String> mFrom) {
        this.mContext = mContext;
        this.mteacherName = mExamName;
        this.mEmail = mFrom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_holder,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("secondtry","onBindViewHolder called");

        holder.TeacherName.setText(mteacherName.get(position));
        holder.memail.setText(mEmail.get(position));
    }

    @Override
    public int getItemCount() {
        return mteacherName.size();
    }



    //ViewHolder
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private RequestQueue queue,mSpinnerRequest,mAcceptTeacherRequest;
        TextView memail,TeacherName;
        ImageView mCancel;
        com.google.android.material.button.MaterialButton mConfirm,madd;
        View alertMes;
        Spinner Stream,Subject;
        Dialog MyDialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TeacherName = itemView.findViewById(R.id.TeacherName);
            memail = itemView.findViewById(R.id.TeacherEmail);
            mCancel = itemView.findViewById(R.id.mCancel);
            mConfirm = itemView.findViewById(R.id.confirm);
            alertMes = itemView;
            queue = Volley.newRequestQueue(itemView.getContext());
            mSpinnerRequest = Volley.newRequestQueue(itemView.getContext());
            mAcceptTeacherRequest = Volley.newRequestQueue(itemView.getContext());

            MyDialog = new Dialog(itemView.getContext());
            MyDialog.setContentView(R.layout.teacher_confirmation_popup);
            madd = MyDialog.findViewById(R.id.addTeacher);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RejectTeacher(memail.getText().toString());
                    mteacherName.remove(getAdapterPosition());
                    mEmail.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),mteacherName.size());
                }
            });
            fetchSpinnerItems();

            mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    MyDialog.show();


                }
            });

            madd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AcceptTeacher(memail.getText().toString(),
                            Stream.getSelectedItem().toString(),
                            Subject.getSelectedItem().toString());
                    MyDialog.cancel();
                    mteacherName.remove(getAdapterPosition());
                    mEmail.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),mteacherName.size());



                    AlertDialog alertDialog = new AlertDialog.Builder(alertMes.getContext()).create();
                    alertDialog.setMessage("Teacher was successfully added");
                    alertDialog.show();
                }
            });

        }


        public void RejectTeacher(final String email){


            String url = "http://kilishi.co.ke/Android_new_teacher_reject.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response

                            Log.d("Response", response);

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog alertDialog = new AlertDialog.Builder(alertMes.getContext()).create();
                            alertDialog.setMessage("Your request timed out");
                            alertDialog.show();
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("email", email);


                    return params;
                }
            };
            queue.add(postRequest);


        }

        private void fetchSpinnerItems() {
            Log.d("testdata","Hello");

            JsonArrayRequest req = new JsonArrayRequest("http://kilishi.co.ke/Android_fill_teacher_spinners.php", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    Log.d("testdata",Integer.toString(response.length()));
                    Stream = MyDialog.findViewById(R.id.Stream);
                    Subject = MyDialog.findViewById(R.id.Subject);
                    List<String> Streamslist = new ArrayList<String>();
                    List<String> subjectlist = new ArrayList<String>();

                    try {
                        for (int i = 0; i < response.getJSONArray(0).length();i++){

                            JSONObject jsonObject = response.getJSONArray(0).getJSONObject(i);




                            Streamslist.add(jsonObject.get("StreamClass").toString()+" "+jsonObject.get("StreamName").toString());


                            //Log.d("testdata",jsonObject.toString());

                        }
                        for (int i = 0; i < response.getJSONArray(1).length();i++){

                            JSONObject jsonObject = response.getJSONArray(1).getJSONObject(i);


                            subjectlist.add(jsonObject.get("SubjectName").toString());

                            //Log.d("testdata",jsonObject.toString());

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("testdata",e.toString());

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(alertMes.getContext(),
                            R.layout.custom_spinner_items, Streamslist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Stream.setAdapter(dataAdapter);

                    ArrayAdapter<String> SubjectAdapter = new ArrayAdapter<String>(alertMes.getContext(),
                            R.layout.custom_spinner_items, subjectlist);
                    SubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Subject.setAdapter(SubjectAdapter);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog alertDialog = new AlertDialog.Builder(alertMes.getContext()).create();
                            alertDialog.setMessage("Your request timed out,Please check your internet connection");
                            alertDialog.show();
                        }
                    }
            );


            mSpinnerRequest.add(req);
        }

        public void AcceptTeacher(final String email,final String stream,final String SubjectName){


            String url = "http://kilishi.co.ke/Android_new_teacher_confirm.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response

                            Log.d("Response", response);

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog alertDialog = new AlertDialog.Builder(alertMes.getContext()).create();
                            alertDialog.setMessage("Your request timed out");
                            alertDialog.show();
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("stream",stream);
                    params.put("subject",SubjectName);

                    return params;
                }
            };
            mAcceptTeacherRequest.add(postRequest);


        }



    }


}
