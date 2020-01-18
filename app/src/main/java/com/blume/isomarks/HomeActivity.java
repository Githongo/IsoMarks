package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blume.subjectRecyclerView.Subject;
import com.blume.subjectRecyclerView.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mSubjectRecyclerView;
    private RecyclerView.Adapter mSubjectAdapter;
    private RecyclerView.LayoutManager mSubjectLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Subjects");

        //finding recycler view, setting attr. and creating helper class objects
        mSubjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        mSubjectRecyclerView.setNestedScrollingEnabled(false);
        mSubjectRecyclerView.setHasFixedSize(true);
        mSubjectLayoutManager = new LinearLayoutManager(HomeActivity.this);
        mSubjectRecyclerView.setLayoutManager(mSubjectLayoutManager);
        mSubjectAdapter = new SubjectAdapter(getDataSetSubject(), HomeActivity.this);
        mSubjectRecyclerView.setAdapter(mSubjectAdapter);
    }


    //inflating toolbar(appbar) with menu res
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //handling options selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.actionbar_help){
            Toast.makeText(HomeActivity.this, "Google it...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.actionbar_settings){
            Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            //insert settings code here...
        }
        if (itemId == R.id.actionbar_logout){
            //Insert Logout code here...if any ;)!
        }

        return super.onOptionsItemSelected(item);
    }

    Subject subobj = new Subject("1234", "Mathematics", "4 D", "Opener", "Editable");
    Subject subobj2 = new Subject("3456", "English", "4 S", "End Term", "Editable");

    ArrayList resultsSubjects = new ArrayList<Subject>();
    List<Subject> getDataSetSubject() {
        return getResultsSubjects();
    }

    public ArrayList getResultsSubjects() {
        resultsSubjects.add(subobj);
        resultsSubjects.add(subobj2);
        return resultsSubjects;
    }
}
