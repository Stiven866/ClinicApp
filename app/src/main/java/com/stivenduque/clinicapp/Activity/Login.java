package com.stivenduque.clinicapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    EditText etIdUser, etPass, etEmail;
    TextView tvGoToRegister;
    Button btnLogin;
    private static int REGISTER_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();

    }





    public void loginUser(){
        if (etIdUser.getText().toString().isEmpty()){//*************************
            etIdUser.setError(getString(R.string.login_error_idUser));//*************************
        }else if (etPass.getText().toString().isEmpty()) {
            etPass.setError(getString(R.string.login_error_pass));
        }
        else {
            String url =getResources().getString(R.string.url)+"Login.php?id="+etIdUser.getText().toString()+"&password="+etPass.getText().toString();
            url = url.replace(" ","%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
            request.add(jsonObjectRequest);
        }
    }


    private void initApp() {
        Intent intent = new Intent(Login.this,MenuProfile.class);
        startActivity(intent);
        finish();
    }
    private void initUi() {
        etIdUser = findViewById(R.id.et_login_idUser);
        etPass = findViewById(R.id.et_login_pass);
        tvGoToRegister = findViewById(R.id.linkLoginToRegister);
        etIdUser.setHintTextColor(getResources().getColor(R.color.textApp));
        etPass.setHintTextColor(getResources().getColor(R.color.textApp));
        tvGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        request = Volley.newRequestQueue(this);
        btnLogin = findViewById(R.id.btn_login_deposit);
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void goToRegister() {
        Intent intent = new Intent(Login.this, RegisterTabs.class);
        startActivityForResult(intent, REGISTER_REQUEST);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "El usuario no existe "+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("NoExiste",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("fail"))){
                if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("no_existent"))){
                    SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    User user = new User();
                    user.setId(jsonObject.optString("id"));
                    user.setPsw(jsonObject.getString("password"));
                    user.setEmail(jsonObject.getString("email"));
                    editor.putString("id", user.getId());
                    editor.commit();
                    Log.d("User login SQL", "login");
                    initDataBase(user.getEmail(), user.getPsw());
                    initApp();
                }else{
                    Toast.makeText(this,"No estas registrado ",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initDataBase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                        }else{
                            Log.d("User login fireBase","Error en la cuenta");
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Log.d("User login fireBase","ya esta logueado");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}