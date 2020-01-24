package com.blume.isomarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.blume.edithelpers.EditAdapter;
import com.blume.edithelpers.EditModel;

import java.util.ArrayList;

public class EditmarksActivity extends AppCompatActivity {
    private ListView editLv;
    private EditAdapter editAdapter;
    public ArrayList<EditModel> editModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmarks);
        //initializing toolbar
        Toolbar myToolbar = findViewById(R.id.editmarks_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(getIntent().getExtras().getString("subjectName")+", "+getIntent().getExtras().getString("stream"));

        //initializing lv
        editLv = (ListView) findViewById(R.id.editListView);

        editModelArrayList = populateList();
        editAdapter = new EditAdapter(this,editModelArrayList);
        editLv.setAdapter(editAdapter);

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
        if (itemId == R.id.editmarks_menu_save){
            String Mark = EditAdapter.editModelArrayList.get(0).getEditTextValue();
            Toast.makeText(EditmarksActivity.this, Mark, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<EditModel> populateList() {
        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(String.valueOf(i));
            list.add(editModel);
        }

        return list;
    }


}
