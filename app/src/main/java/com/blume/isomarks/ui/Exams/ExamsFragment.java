package com.blume.isomarks.ui.Exams;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamsFragment extends Fragment {


    private ArrayList<String> mExamName = new ArrayList<>();
    private ArrayList<String> mFrom = new ArrayList<>();
    private ArrayList<String> mTo = new ArrayList<>();

    private ArrayList<String> pExamName = new ArrayList<>();
    private ArrayList<String> pFrom = new ArrayList<>();
    private ArrayList<String> pTo = new ArrayList<>();

    private RequestQueue mRequestQueue,queue;
    ExamsAdapter adapter;
    PastExamsAdapter pastadapter;
    RecyclerView recyclerView,pastRecyclerview;
    ImageButton addButton;
    com.google.android.material.button.MaterialButton addExam;
    com.google.android.material.textfield.TextInputEditText mexamname,FromDate,ToDate;
    Spinner ExamType, Term;
    DatePickerDialog datePickerDialog;
    View root;
    Dialog myDialog;
    //String ExamName,Examtype,term,mfrom,mto;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_exams, container, false);


        getActivity().setTitle("Exams");
        myDialog = new Dialog(getContext());


        mRequestQueue = Volley.newRequestQueue(getContext());
        queue = Volley.newRequestQueue(getContext());

        VolleyCallback volleyCallback=new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    mExamName.add((String)jsonObject.get("ExamName"));
                    mFrom.add((String)jsonObject.get("fromDate"));
                    mTo.add((String)jsonObject.get("toDate"));
                    Log.d("volleyTest",Integer.toString(mExamName.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void PastonSuccess(JSONObject jsonObject) {
                try {
                    pExamName.add((String)jsonObject.get("ExamName"));
                    pFrom.add((String)jsonObject.get("fromDate"));
                    pTo.add((String)jsonObject.get("toDate"));
                    Log.d("volleyTest",Integer.toString(pExamName.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        recyclerView = root.findViewById(R.id.examsList);
        adapter = new ExamsAdapter(getContext(),mExamName,mFrom,mTo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        pastRecyclerview = root.findViewById(R.id.PastExams);
        pastadapter = new PastExamsAdapter(getContext(),pExamName,pFrom,pTo);
        pastRecyclerview.setAdapter(pastadapter);
        pastRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        fetchJsonResponse(volleyCallback);




        addButton = root.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.exam_popup_window);

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();


                FromDate = myDialog.findViewById(R.id.FromD);
                ToDate = myDialog.findViewById(R.id.ToD);
                addExam = myDialog.findViewById(R.id.addExam);

                mexamname = myDialog.findViewById(R.id.examName);
                ExamType = myDialog.findViewById(R.id.examType);
                Term = myDialog.findViewById(R.id.term);

                //display the datepicker when field is clicked
                FromDateDisplay();
                ToDateDisplay();

                addItemsOnExamType();
                addItemsOnTerm();

                addExam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mexamname.toString().equals("")&& !FromDate.toString().equals("")
                        && !ToDate.toString().equals("")){
                            postExam();
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setMessage("Exam Posted successfully.");
                            alertDialog.show();
                            myDialog.cancel();
                        }else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setMessage("Please fill all the fields");
                            alertDialog.show();

                        }

                    }
                });

            }
        });






        return root;
    }

    private void initRecyclerView(){

        adapter = new ExamsAdapter(getContext(),mExamName,mFrom,mTo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        pastRecyclerview = root.findViewById(R.id.PastExams);
        pastadapter = new PastExamsAdapter(getContext(),pExamName,pFrom,pTo);
        pastRecyclerview.setAdapter(pastadapter);
        pastRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }


    private void fetchJsonResponse(final VolleyCallback volleyCallback) {

        JsonArrayRequest req = new JsonArrayRequest("http://kilishi.co.ke/Android_list_exams.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        if((jsonObject.get("Status")).equals("1")){
                            volleyCallback.onSuccess(jsonObject);
                        }
                        else {volleyCallback.PastonSuccess(jsonObject);}



                    }catch (JSONException e){
                    }
                }

                initRecyclerView();

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        mRequestQueue.add(req);
    }

    public interface VolleyCallback{

        void onSuccess(JSONObject jsonObject);
        void  PastonSuccess(JSONObject jsonObject);
    }

    private void FromDateDisplay(){
        FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                FromDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void ToDateDisplay() {
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                ToDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    public void addItemsOnExamType() {

        ExamType = myDialog.findViewById(R.id.examType);
        List<String> list = new ArrayList<String>();

        list.add("Opener Exam");
        list.add("Mid-Term Exam");
        list.add("End Term Exam");
        list.add("FORM 4");
        list.add("JOINT EXAM");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_items, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ExamType.setAdapter(dataAdapter);
    }

    public void addItemsOnTerm() {

        Term = myDialog.findViewById(R.id.term);
        List<String> list = new ArrayList<String>();

        list.add("1");
        list.add("2");
        list.add("3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_items, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Term.setAdapter(dataAdapter);
    }

    public void postExam(){


        String url = "http://kilishi.co.ke/Android_create_exam.php";
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
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setMessage("Your request timed out");
                        alertDialog.show();
                        // error
                        //Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Examname", mexamname.getText().toString());
                params.put("From", FromDate.getText().toString());
                params.put("To", ToDate.getText().toString());
                params.put("ExamType", ExamType.getSelectedItem().toString());
                params.put("Term", Term.getSelectedItem().toString());

                return params;
            }
        };
        queue.add(postRequest);


    }

    }