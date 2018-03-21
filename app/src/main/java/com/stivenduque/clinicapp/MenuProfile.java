package com.stivenduque.clinicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.io.Serializable;
import java.util.Map;

public class MenuProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvDrawerUsername, tvDrawerEmail, tvTypeUser;
    final static int INTENT_MENU_PROFILE=2;
    Map<String, String> dataUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Serializable extras = getIntent().getSerializableExtra("dataUser");
        dataUser = (Map<String, String>) extras;
      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        //View view = navigationView.inflateHeaderView(R.layout.nav_header_menu_profile);
        tvDrawerUsername =  headerView.findViewById(R.id.tv_drawer_user_name);
        tvDrawerEmail = headerView.findViewById(R.id.tv_drawer_user_email);
        tvTypeUser = headerView.findViewById(R.id.tv_drawer_user_type);



        if(dataUser.get("typeUser").equals("medic")){
            tvDrawerUsername.setText(dataUser.get("userName"));
            tvDrawerEmail.setText(dataUser.get("email"));
            tvTypeUser.setText("Medico");
        }else if(dataUser.get("typeUser").equals("patient")){
            Log.d("EYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY", dataUser.get("typeUser"));
            tvDrawerUsername.setText(dataUser.get("userName"));
            tvDrawerEmail.setText(dataUser.get("email"));
            tvTypeUser.setText("Paciente");
        }

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
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.my_profile:
                Intent intent = new Intent(MenuProfile.this,ViewProfile.class);
                intent.putExtra("dataUser", (Serializable) dataUser);
                startActivityForResult(intent,INTENT_MENU_PROFILE);
                break;
            case R.id.close_seccion:
               Intent i = new Intent(MenuProfile.this, Login.class);
               i.putExtra("dataUser", (Serializable) dataUser);
               setResult(RESULT_OK,i);
               finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_MENU_PROFILE && requestCode == RESULT_OK){

        }else if (resultCode == RESULT_CANCELED){
            Toast.makeText(MenuProfile.this, "no se puede mostrar información", Toast.LENGTH_SHORT).show();
        }
    }
}
