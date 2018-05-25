package com.stivenduque.clinicapp.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
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
import com.stivenduque.clinicapp.Fragments.MyHealth;
import com.stivenduque.clinicapp.Fragments.BlankFragment3;
import com.stivenduque.clinicapp.Fragments.MyMedicsFragment;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    TextView tvDrawerUsername, tvDrawerEmail, tvTypeUser;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    private String idPreferences = null;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private MenuItem mImyAccount, mIaddFamiliary,mIchangePass,mIchangeLanguage,mImyHistory,mImyDoctors,mIpharmacies,mImedicCenter;
    private int flag = 0;
    Fragment fragment = null;


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
        Menu menuBnav = bottomNavigationView.getMenu();
        mImyHistory = menuBnav.findItem(R.id.my_history);
        mImyDoctors = menuBnav.findItem(R.id.my_doctors);
        mIpharmacies = menuBnav.findItem(R.id.pharmacies);
        mImedicCenter = menuBnav.findItem(R.id.medic_center);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_history:
                        fragment = new BlankFragment3();
                        break;
                    case R.id.my_doctors:
                        fragment = new MyMedicsFragment();
                        break;
                    case R.id.pharmacies:
                        goToMapsPharmacies();
                        mIpharmacies.setChecked(false);
                        return true;
                        //break;
                    case R.id.medic_center:
                        fragment = new BlankFragment3();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        request = Volley.newRequestQueue(this);

        //setItemBottomNavigationView(flag);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menuNav = navigationView.getMenu();
        mImyAccount = menuNav.findItem(R.id.nav_my_account);
        mIaddFamiliary = menuNav.findItem(R.id.nav_add_familiary);
        mIchangeLanguage = menuNav.findItem(R.id.nav_change_language);
        mIchangePass = menuNav.findItem(R.id.nav_change_pass);



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
        Log.d("abierto", "no");
        switch (item.getItemId()){
            case R.id.nav_my_account:
                Log.d("abierto", "yes");
                closeDrawer(item);
                goToAccount();
                mImyAccount.setChecked(false);
                mImyAccount.setCheckable(false);
                return true;
            case R.id.nav_add_familiary:
                fragment = new BlankFragment3();
                break;
            case R.id.nav_change_pass:
                fragment = new BlankFragment3();
                break;
            case R.id.nav_change_language:
                goToChat();
                mIchangeLanguage.setChecked(false);
                mIchangeLanguage.setCheckable(false);
                return true;
            case R.id.nav_close_session:
                logOut();
                return true;
            case R.id.nav_get_out:
                finish();
                return true;
        }
        replaceFragment(fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void closeDrawer(MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem.setChecked(false);
    }


    @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, new BlankFragment3());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
    private boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.toString().equals("com.android.volley.TimeoutError")){
            Toast.makeText(this, "No tiene acceso a internet",Toast.LENGTH_SHORT).show();
            finish();

        }else {
            Toast.makeText(this, "El usuario no existe "+ error.toString(),Toast.LENGTH_SHORT).show();
            Log.d("NoExiste",error.toString());
        }
    }

    @Override
    public void onResponse(JSONObject response) {
       User user = new User();
        //Toast.makeText(this, "Message: "+ response,Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("fail"))) {
                if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("no_existent"))) {
                    user.setName(jsonObject.optString("name"));
                    user.setLasName(jsonObject.optString("lastName"));
                    user.setEmail(jsonObject.optString("email"));
                }else{
                    Toast.makeText(this,"Error en la conexion", Toast.LENGTH_SHORT).show();
                    logOut();
                }

            }else {
                logOut();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvDrawerEmail.setText(user.getEmail());
        tvDrawerUsername.setText(user.getName() + " "+ user.getLasName());
    }

    @Override
    protected void onStart() {
        super.onStart();



        if (isNetworkConnected(this) == true){
            loadPreferences();
            if (idPreferences == null){
                //Log.d("User", idPreferences);
                logOut();
                goToLogin();
            }else {
                Log.d("User", "Usuario logueado");
                String url = getResources().getString(R.string.url)+ "Consulta.php?id="+idPreferences;
                Log.d("User", url);
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
                request.add(jsonObjectRequest);
            }
        }
        }


    @Override
    protected void onStop() {
        super.onStop();
        //FirebaseAuth.getInstance().signOut();

    }

    private void loadPreferences(){
        sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        idPreferences = sharedPreferences.getString("id",null);
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
    public void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", null);
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

    private void goToMapsPharmacies() {
        Intent intent = new Intent(MenuProfile.this, MapsPharmacies.class);
        startActivityForResult(intent,0);
    }
}


