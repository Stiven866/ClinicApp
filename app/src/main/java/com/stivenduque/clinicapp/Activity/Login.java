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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

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
    Map<String, String> hashMap;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient mgoogleApiClient;
    private SignInButton btnSignInGoogle;
    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;
    public static final int LOGIN_CON_GOOGLE = 777;

    ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initDataBase();
        initUi();

    }

    private void initDataBase() {
       /* //Inicio con correo electronico
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("SESION", "sesion iniciada con email: " + user.getEmail());
                    initApp();
                } else {
                    Log.d("SESION", "sesion cerrada");
                }
            }
        };

        //Inicio con cuenta de Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/
    }



    public void loginUser(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando usuario...");
        progressDialog.show();
        if (etIdUser.getText().toString().isEmpty()){//*************************
            etIdUser.setError(getString(R.string.login_error_idUser));//*************************
        }else if (etPass.getText().toString().isEmpty()) {
            etPass.setError(getString(R.string.login_error_pass));
        }
        else {
            String url =getResources().getString(R.string.url)+"Login.php?id="+etIdUser.getText().toString()+"&password="+etPass.getText().toString();
            //Log.d("User", url);
            url = url.replace(" ","%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
            request.add(jsonObjectRequest);
            //checkedUserSession(etIdUser.getText().toString(),etPass.getText().toString());
        }
    }



    private void checkedUserSession(String email, String pass) {
        /*mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("SESION", "Ususario iniciado correctamente");
                    Toast.makeText(getApplicationContext(),"Cuenta Creada",Toast.LENGTH_SHORT).show();
                    initApp();
                }else{

                    Log.d("SESION", task.getException().getMessage()+"");
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Log.d("eeeee", String.valueOf(requestCode));
        if (requestCode == LOGIN_CON_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSinginInResult(result);
        }

    }*/

    /*private void handleSinginInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        }else{
            Log.d("SESION WHIT GOOGLE", "No se pudo iniciar");
        }
    }*/

   /* private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d("SESION WHIT GOOGLE:", "Ha salido");
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Log.d("SESION WHIT GOOGLE", "Usuario ya registrado");
                                Toast.makeText(getApplicationContext(),"Usuario ya registrado",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                            }else{
                                Log.d("SESION WHIT GOOGLE","Error en la cuenta");
                                Toast.makeText(getApplicationContext(),"Error en la cuenta",Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                });
    }*/



    private void initApp() {
        Intent intent = new Intent(Login.this,MenuProfile.class);
        startActivity(intent);
        finish();
    }
    private void initUi() {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        //callbackManager = CallbackManager.Factory.create();

        etIdUser = findViewById(R.id.et_login_idUser);//*************************
        //etEmail = findViewById(R.id.et_login_email);
        etPass = findViewById(R.id.et_login_pass);
        tvGoToRegister = findViewById(R.id.linkLoginToRegister);
        etIdUser.setHintTextColor(getResources().getColor(R.color.textApp));//*************************
        etPass.setHintTextColor(getResources().getColor(R.color.textApp));
        //etEmail.setHintTextColor(getResources().getColor(R.color.textApp));
        tvGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        request = Volley.newRequestQueue(this);
        btnLogin = findViewById(R.id.btn_login_deposit);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

       /* btnSignInGoogle = findViewById(R.id.login_button_google);
        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
                startActivityForResult(intent,LOGIN_CON_GOOGLE);
            }
        });

        btnLoginFacebook = findViewById(R.id.login_button_fb);
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                //Log.d("SESION WHIT FACEBOOK:", "Se ha iniciado sesion con facebook");
            }

            @Override
            public void onCancel() {
                Log.d("SESION WHIT FACEBOOK:", "Se ha cancelado el inicio de sesion con facebook");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("SESION WHIT FACEBOOK:", "Ha salido un error de inicio de sesion con facebook");
            }
        });*/
    }
    /*private  void handleFacebookAccessToken(AccessToken accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SESION WHIT FACEBOOK:", "Ha salido");
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Log.d("SESION WHIT FACEBOOK", "Usuario ya registrado");
                        Toast.makeText(getApplicationContext(),"Usuario ya registrado",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                    }else{
                        Log.d("SESION WHIT FACEBOOK","Error en la cuenta");
                        Toast.makeText(getApplicationContext(),"Error en la cuenta",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }*/

    private void goToRegister() {
        Intent intent = new Intent(Login.this, RegisterTabs.class);
        startActivityForResult(intent, REGISTER_REQUEST);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if (authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }*/

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(this, "El usuario no existe "+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("NoExiste",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        User user = new User();
        Toast.makeText(this, "Se ha registrado correctamente",Toast.LENGTH_SHORT).show();

        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setId(jsonObject.optString("id"));
            SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", user.getId());
            editor.commit();
            initApp();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.hide();
    }

}