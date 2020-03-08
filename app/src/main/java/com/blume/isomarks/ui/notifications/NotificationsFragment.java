package com.blume.isomarks.ui.notifications;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.List;

public class NotificationsFragment extends Fragment {
    private ArrayList<String> mteacherName = new ArrayList<>();
    private ArrayList<String> mEmail = new ArrayList<>();
    private RequestQueue mRequestQueue;
    RecyclerView NotificationRecyclerview;
    NotificationsAdapter notificationsAdapter;
    LinearLayout mEmptylist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        getActivity().setTitle("Notifications");
        mRequestQueue = Volley.newRequestQueue(getContext());
        NotificationRecyclerview = root.findViewById(R.id.NotificationRec);
        mEmptylist = root.findViewById(R.id.emptyList);
        notificationsAdapter = new NotificationsAdapter(getContext(),mteacherName,mEmail);
        NotificationRecyclerview.setAdapter(notificationsAdapter);
        NotificationRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));



        VolleyCallback volleyCallback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    mteacherName.add((String)jsonObject.get("Username"));
                    mEmail.add((String)jsonObject.get("Email"));
                    Log.d("volleyTest",Integer.toString(mteacherName.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        fetchJsonResponse(volleyCallback);




        return root;
    }

    private void initRecyclerView(){
        notificationsAdapter = new NotificationsAdapter(getContext(),mteacherName,mEmail);
        NotificationRecyclerview.setAdapter(notificationsAdapter);
        if (notificationsAdapter.getItemCount() != 0){
            mEmptylist.setVisibility(View.GONE);}else {
            mEmptylist.setVisibility(View.VISIBLE);
        }

        NotificationRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void fetchJsonResponse(final VolleyCallback volleyCallback) {

        JsonArrayRequest req = new JsonArrayRequest("http://kilishi.co.ke/Android_new_teacher_notification.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                            volleyCallback.onSuccess(jsonObject);




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