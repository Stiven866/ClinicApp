package com.stivenduque.clinicapp.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import android.support.design.widget.BottomNavigationView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.stivenduque.clinicapp.Fragments.BlankFragment3;
import com.stivenduque.clinicapp.Fragments.BlankFragment4;
import com.stivenduque.clinicapp.Fragments.MyMedicsFragment;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    TextView tvDrawerUsername, tvDrawerEmail, tvTypeUser;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    private String idPreferences;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


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
                        fragment = new BlankFragment4();
                        break;
                    case R.id.my_doctors:
                        fragment = new MyMedicsFragment();
                        break;
                    case R.id.pharmacies:

                        break;
                    case R.id.medic_center:
                        fragment = new BlankFragment4();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        request = Volley.newRequestQueue(this);


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
                item.setChecked(false);
                goToAccount();
                return true;
            case R.id.nav_add_familiary:
                fragment = new BlankFragment3();
                break;
            case R.id.nav_change_pass:
                fragment = new MyMedicsFragment();
                break;
            case R.id.nav_change_language:
                //fragment = new MyAccountPatient();
                goToChat();
                return true;
            case R.id.nav_close_session:
                logOut();
                return true;
            case R.id.nav_get_out:
                //fragment = new MyAccountPatient();
                finish();
                return true;
        }
        replaceFragment(fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {}

    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, new BlankFragment4());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void logOut() {
        /*firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();*/
        loadPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "0");
        editor.commit();
        goToLogin();
    }

    private void goToLogin() {
        Intent intent = new Intent(MenuProfile.this,Login.class);
        startActivity(intent);
        finish();
    }
    private void goToAccount(){
        Intent intent = new Intent(MenuProfile.this, MyAccount.class);
        startActivityForResult(intent,0);
        //finish();
    }
    private void goToChat() {
        Intent intent = new Intent(MenuProfile.this, ChatActivity.class);
        startActivityForResult(intent,0);
    }

    private void loadPreferences(){
       sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
       idPreferences = sharedPreferences.getString("id","0");
    }

    private void initDataBase() {
        /*firebaseAuth = FirebaseAuth.getInstance();
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
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso ).build();*/
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No hay consulta "+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("NoExiste",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
       User user = new User();
        //Toast.makeText(this, "Message: "+ response,Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setName(jsonObject.optString("name"));
            user.setLasName(jsonObject.optString("lastName"));
            user.setEmail(jsonObject.optString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvDrawerEmail.setText(user.getEmail());
        tvDrawerUsername.setText(user.getName() + " "+ user.getLasName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*firebaseAuth.addAuthStateListener(authStateListener);

        if (firebaseAuth.getInstance().getCurrentUser() != null){
            Log.d("User", "Usuario logueado");
        }else {
            logOut();
            goToLogin();
        }*/

        loadPreferences();
        if (idPreferences != "0"){
            Log.d("User", "Usuario logueado");
            String url = getResources().getString(R.string.url)+ "Consulta.php?id="+idPreferences;
            Log.d("User", url);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
            request.add(jsonObjectRequest);
        }else {
            logOut();
            goToLogin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }*/

    }
}


