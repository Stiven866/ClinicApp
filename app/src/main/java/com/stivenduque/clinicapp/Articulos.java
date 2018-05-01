package com.stivenduque.clinicapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.stivenduque.clinicapp.entidades.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;


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
                register(etId.getText().toString(), etPass.getText().toString());
            }
        });
        return view;
    }

    /*private void loadWebService() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultando...");
        progressDialog.show();
        String url = "http://192.168.1.7/dbClinicApp/Consulta.php?id="+etId.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }*/

    /*@Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "melo"+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("Hola","No se pudo registrar"+error.toString());

    }


    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        Toast.makeText(getContext(),"Mensaje: " +response, Toast.LENGTH_SHORT).show();
        User user = new User();
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setName(jsonObject.optString("name"));
            user.setLasName(jsonObject.optString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvName.setText(user.getName());
        tvLastName.setText(user.getLasName());
    }*/

    private void register(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("SESION","Cuenta Creada");
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
}
