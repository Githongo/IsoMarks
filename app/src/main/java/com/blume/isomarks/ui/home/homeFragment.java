package com.blume.isomarks.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blume.isomarks.R;
import com.blume.isomarks.TeachersActivity;
import com.blume.isomarks.landing_admin;

public class homeFragment extends Fragment {

    private homeViewModel mhomeViewModel;
    com.google.android.material.card.MaterialCardView mCardview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mhomeViewModel =
                ViewModelProviders.of(this).get(homeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        mhomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mCardview = root.findViewById(R.id.AddTeachIcon);

        mCardview.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TeachersActivity.class);
                startActivity(i);
            }
        });


        return root;
    }
}
