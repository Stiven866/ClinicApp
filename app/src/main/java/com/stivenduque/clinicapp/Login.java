package com.stivenduque.clinicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.Serializable;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText etIdUser, etPass;
    TextView tvGoToRegister;
    Button btnLogin;
    private static int REGISTER_REQUEST = 1;
    Map<String, String> hashMap,dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();



    }
    private void initUi() {
        etIdUser = findViewById(R.id.et_login_idUser);
        etPass = findViewById(R.id.et_login_pass);
        btnLogin = findViewById(R.id.btn_login_deposit);
        tvGoToRegister = findViewById(R.id.linkLoginToRegister);
        etIdUser.setHintTextColor(getResources().getColor(R.color.textApp));
        etPass.setHintTextColor(getResources().getColor(R.color.textApp));

        tvGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void goToRegister() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivityForResult(intent, REGISTER_REQUEST);
    }
    public void loginUser(){
        if (etIdUser.getText().toString().length() <= 0){
            etIdUser.setError(getString(R.string.login_error_idUser));
        }else if (etPass.getText().toString().length() <= 0) {
            etPass.setError(getString(R.string.login_error_pass));
        }
        else {
            checkUserIsMedical();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTER_REQUEST && resultCode == RESULT_OK) {
            hashMap = (Map<String, String>) data.getSerializableExtra("dataToRegister");
            Log.d("Loging", hashMap.toString());
        }
        if (requestCode == 2 && requestCode == RESULT_OK){
            hashMap = (Map<String, String>) data.getSerializableExtra("dataUser");
        }
    }

    private void checkUserIsMedical() {
        final ProgressDialog pd = ProgressDialog.show(this,"Verificando credenciales del usurio", "Esperando respuesta");
        try{
            pd.dismiss();
            if(hashMap.get("identification").equals(etIdUser.getText().toString()) ){
                if(hashMap.get("password").equals(etPass.getText().toString())){
                    initApp();
                }

            }



        }catch (Exception error){
            Log.d("onError", error.getMessage());
        }
    }

    private void initApp() {
        Intent intent = new Intent(this,MenuProfile.class);
        intent.putExtra("dataUser", (Serializable) hashMap);
        startActivityForResult(intent,2);
    }

}