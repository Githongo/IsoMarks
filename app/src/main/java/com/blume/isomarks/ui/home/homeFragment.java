package com.blume.isomarks.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blume.isomarks.MainActivity;
import com.blume.isomarks.R;
import com.blume.isomarks.TeachersActivity;
import com.blume.isomarks.landing_admin;
import com.blume.isomarks.ui.Exams.ExamsFragment;

public class homeFragment extends Fragment {

    private homeViewModel mhomeViewModel;
    com.google.android.material.card.MaterialCardView mTeachersCard,mExamsCard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        mhomeViewModel =
                ViewModelProviders.of(this).get(homeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        getActivity().setTitle("Isomarks");


        mhomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mTeachersCard = root.findViewById(R.id.AddTeachIcon);
        mExamsCard = root.findViewById(R.id.ExamIcon);


        mTeachersCard.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TeachersActivity.class);
                startActivity(i);
            }
        });

        mExamsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (container != null){
                    container.removeAllViews();
                }*/

                Fragment fragment = new ExamsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_out_right,R.anim.slide_in_left);
                fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                fragmentTransaction.addToBackStack(null);
               // fragmentTransaction.remove(new homeFragment());
                fragmentTransaction.commit();
                
            }
        });


        return root;
    }
}
