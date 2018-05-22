package com.stivenduque.clinicapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.stivenduque.clinicapp.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Articulos extends Fragment{ //implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    EditText etId,etPass;
    //TextView tvName, tvLastName;
    Button btnConsult;
    ProgressDialog progressDialog;
    //RequestQueue requestQueue;
    //JsonObjectRequest jsonObjectRequest;
    public Articulos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articulos, container, false);
        etId = view.findViewById(R.id.et_Id);
        etPass = view.findViewById(R.id.et_pass);
        //tvName = view.findViewById(R.id.tv_name);
        //tvLastName = view.findViewById(R.id.tv_lastName);
        btnConsult = view.findViewById(R.id.btn_consultar);

        //requestQueue = Volley.newRequestQueue(getContext());

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadWebService();
                if (etId.getText().toString().isEmpty()){//*************************
                    etId.setError(getString(R.string.login_error_idUser));//*************************
                }else if (etPass.getText().toString().isEmpty()) {
                    etPass.setError(getString(R.string.login_error_pass));
                }
                else {
                    register(etId.getText().toString(), etPass.getText().toString());
                }

            }
        });
        return view;
    }


    private void register(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("SESION","Cuenta Creada");
                    Toast.makeText(getActivity(),"Cuenta Creada",Toast.LENGTH_SHORT).show();
                    goToLogin();
                }else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Log.d("SESION", "Usuario ya registrado");
                    }else{
                        Log.d("SESION","Error en la cuenta");
                    }

                }
            }
        });
    }

    private void goToLogin(){
        Intent intent = new Intent();
        getActivity().setResult(RESULT_OK,intent);
        getActivity().finish();
    }
}
