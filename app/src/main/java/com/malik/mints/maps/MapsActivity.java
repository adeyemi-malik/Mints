package com.malik.mints.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.malik.mints.authentication.LoginActivity;
import com.malik.mints.R;
import com.malik.mints.providers.Injection;
import com.malik.mints.util.ActivityUtils;

public class MapsActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private FirebaseFirestore database;
    private FirebaseAuth auth;

    public String TAG = "MainActivity";


    private MapsPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        updateUi(auth.getCurrentUser());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> Snackbar.make(v, R.string.snarkbar_text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MapsFragment mapsFragment =
                (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (mapsFragment == null) {
            // Create the fragment
            mapsFragment = MapsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mapsFragment, R.id.container);
        }


        // Create the presenter
        presenter = new MapsPresenter(Injection.provideMapsRepository(), mapsFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            MapsFilterType currentFiltering =
                    (MapsFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            presenter.setFiltering(currentFiltering);
        }
    }

    private FirebaseAuth.AuthStateListener authStateListener = auth -> {
        FirebaseUser user = auth.getCurrentUser();
        updateUi(user);
    };

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
        FirebaseUser user = auth.getCurrentUser();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUi(user);
    }

    public void updateUi(FirebaseUser user)
    {
        if(user != null) {

        }
        else{

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, presenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings:
                break;
            case R.id.action_signout:
                signOut();

        }

        return super.onOptionsItemSelected(item);
    }

    public void signOut()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                break;
            case R.id.nav_sign_out:
                signOut();
            case R.id.nav_animals:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_map:
                // signOut();
            case R.id.nav_profile:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
