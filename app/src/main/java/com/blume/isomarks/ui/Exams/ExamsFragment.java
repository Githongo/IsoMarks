package com.blume.isomarks.ui.Exams;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.blume.isomarks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamsFragment extends Fragment {


    private ArrayList<String> mExamName = new ArrayList<>();
    private ArrayList<String> mFrom = new ArrayList<>();
    private ArrayList<String> mTo = new ArrayList<>();
    private RequestQueue mRequestQueue;
    ExamsAdapter adapter;
    RecyclerView recyclerView;
    ImageButton addButton;
    View root;
    Dialog myDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_exams, container, false);
        /*final TextView textView = root.findViewById(R.id.text_schools);
        examsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        getActivity().setTitle("Exams");
        myDialog = new Dialog(getContext());


        mRequestQueue = Volley.newRequestQueue(getContext());

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
        };
        recyclerView = root.findViewById(R.id.examsList);
        adapter = new ExamsAdapter(getContext(),mExamName,mFrom,mTo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        fetchJsonResponse(volleyCallback);

        addButton = root.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.exam_popup_window);

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });




        return root;
    }

    private void initRecyclerView(){

        adapter = new ExamsAdapter(getContext(),mExamName,mFrom,mTo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


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
    }


}