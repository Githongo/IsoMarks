package com.blume.isomarks;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.blume.isomarks.ui.Account.AccountFragment;
import com.blume.isomarks.ui.Exams.ExamsFragment;
import com.blume.isomarks.ui.home.homeFragment;
import com.blume.isomarks.ui.notifications.NotificationsFragment;
import com.blume.isomarks.ui.settings.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import org.json.JSONObject;

public class landing_admin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

/*    String data;
    landing_admin(String result){
        data = result;
    }*/
    DrawerLayout dLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_admin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.nav_host_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            homeFragment firstFragment = new homeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_host_fragment, firstFragment).commit();

        }


        setNavigationDrawer();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_admin, menu);
        return true;
    }


    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = findViewById(R.id.nav_view); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.nav_home) {
                    frag = new homeFragment();
                } else if (itemId == R.id.nav_account) {
                    frag = new AccountFragment();
                } else if (itemId == R.id.nav_schools) {
                    frag = new ExamsFragment();
                }else if (itemId == R.id.nav_notifications) {
                    frag = new NotificationsFragment();
                }else if (itemId == R.id.nav_settings) {
                    frag = new SettingsFragment();
                }
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, frag); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                return false;
            }
        });

        toggle = new ActionBarDrawerToggle(this,
                dLayout,
                R.string.open,
                R.string.close);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dLayout.isDrawerOpen(GravityCompat.START)) {
                    dLayout.closeDrawer(GravityCompat.START);
                    Log.d("drawer","closed");

                } else {
                    dLayout.openDrawer(GravityCompat.START);
                    Log.d("drawer","opened");
                }
            }
        });

    }


}
