package com.stivenduque.clinicapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import android.support.design.widget.BottomNavigationView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    TextView tvDrawerUsername, tvDrawerEmail, tvTypeUser;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initDataBase();
        setInitialFragment();
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.my_history:
                        fragment = new BlankFragment();
                        break;
                    case R.id.my_doctors:
                        fragment = new BlankFragment2();
                        break;
                    case R.id.pharmacies:
                        fragment = new BlankFragment3();
                        break;
                    case R.id.medic_center:
                        fragment = new BlankFragment4();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        tvDrawerUsername = headerView.findViewById(R.id.tv_drawer_user_name);
        tvDrawerEmail = headerView.findViewById(R.id.tv_drawer_user_email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.my_profile:
                break;
            case R.id.close_seccion:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_my_account:
                fragment = new BlankFragment4();
                break;
            case R.id.nav_add_familiary:
                fragment = new BlankFragment3();
                break;
            case R.id.nav_change_pass:
                fragment = new BlankFragment2();
                break;
            case R.id.nav_change_language:
                fragment = new BlankFragment();
                break;
            case R.id.nav_close_session:
                fragment = new BlankFragment();
                logOut();
                break;
            case R.id.nav_get_out:
                fragment = new BlankFragment();
                finish();
                break;
        }
        replaceFragment(fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, new BlankFragment());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void initDataBase() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d("SESION", "sesion iniciada con email: "+ user.getEmail());
                }else{
                    Log.d("SESION", "sesion cerrada");
                    goToLogin();
                }
            }
        };
        //Inicio con cuenta de Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso ).build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);

        if (firebaseAuth.getInstance().getCurrentUser() != null){
            Log.d("User", "Usuario logueado");
        }else {
            logOut();
            goToLogin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void logOut() {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    private void goToLogin() {
        Intent intent = new Intent(MenuProfile.this,Login.class);
        startActivity(intent);
        finish();
    }

}


