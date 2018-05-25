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
public class MyHealth extends Fragment{ //implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    EditText etId,etPass;
    //TextView tvName, tvLastName;
    Button btnConsult;
    ProgressDialog progressDialog;
    //RequestQueue requestQueue;
    //JsonObjectRequest jsonObjectRequest;
    public MyHealth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_health, container, false);

        return view;
    }



    private void goToLogin(){
        Intent intent = new Intent();
        getActivity().setResult(RESULT_OK,intent);
        getActivity().finish();
    }
}
