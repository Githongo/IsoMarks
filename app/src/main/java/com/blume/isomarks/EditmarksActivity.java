package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EditmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmarks);

        Toolbar myToolbar = findViewById(R.id.editmarks_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(getIntent().getExtras().getString("subjectName")+", "+getIntent().getExtras().getString("stream"));
    }

    //inflating toolbar with menu res
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmarks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.editmarks_menu_sort_a_to_z){
            Toast.makeText(EditmarksActivity.this, "Ntakusort...", Toast.LENGTH_LONG).show();
        }
        if (itemId == R.id.editmarks_menu_sort_by_admno){
            Toast.makeText(EditmarksActivity.this, "Sorted...", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
