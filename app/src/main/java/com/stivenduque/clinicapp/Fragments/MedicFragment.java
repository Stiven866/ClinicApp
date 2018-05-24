package com.stivenduque.clinicapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.Entidades.Usuario;
import com.stivenduque.clinicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MedicFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{
    EditText etUserName, etLastName, etEmail, etIdentification, etPassword, etPasswordRepeat, etAge, etCivilState, etPhone;
    TextView tvGgoToLogin;
    Button btnRegister;
    String typeUserValue, sex;
    RadioButton rbtnMale, rbtnFamale;
    Map<String, String> dataToRegister;
    User user = new User();
    //ProgressDialog progressDialog;
    boolean flagPrueba = false;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private FirebaseDatabase database;
    //private DatabaseReference referenceUser;
    private FirebaseAuth mAuth;
    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medic, container, false);
        activity = getActivity();
        initUi(view);
        initActions();
        return view;
    }

    private void initUi(View view) {
        etUserName = view.findViewById(R.id.et_register_names_medic);
        etLastName = view.findViewById(R.id.et_register_lastnames_medic);
        etEmail = view.findViewById(R.id.et_register_email_medic);
        etIdentification = view.findViewById(R.id.et_register_identification_medic);
        etPassword = view.findViewById(R.id.et_register_password_medic);
        etPasswordRepeat = view.findViewById(R.id.et_register_password_repeat_medic);
        etAge = view.findViewById(R.id.et_register_age_medic);
        //etCivilState = view.findViewById(R.id.et_register_civil_state_medic);
        etPhone = view.findViewById(R.id.et_register_phone_medic);
        rbtnFamale = view.findViewById(R.id.rbFamale_medic);
        rbtnMale = view.findViewById(R.id.rbMale_medic);
        //tvGgoToLogin = view.findViewById(R.id.txv_goto_login);
        btnRegister = view.findViewById(R.id.btn_register_medic);
        database = FirebaseDatabase.getInstance();
        //referenceUser = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();
        request = Volley.newRequestQueue(getContext());
    }
    public void initActions() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }

        });
    }

    private void registerUser() {
        sexUser();
        if (validateRegister()) {
            user.setName(etUserName.getText().toString());
            user.setLasName(etLastName.getText().toString());
            user.setId(etIdentification.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setPsw(etPassword.getText().toString());
            user.setAge(etAge.getText().toString());
            user.setSex(sex);
            user.setPhone(etPhone.getText().toString());
            if (initDataBase()){
                goToLogin();
            }else {
                //Toast.makeText(activity,"Algo raro pasa",Toast.LENGTH_SHORT).show();
            }

        } else {

        }
    }

    private void sexUser() {
        if (rbtnFamale.isChecked()) {
            sex = "Mujer";
        } else if (rbtnMale.isChecked()) {
            sex = "Hombre";
        }
    }


    private void loadWebService() {
        //progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Cargando...");
        //progressDialog.show();
        String url = getResources().getString(R.string.url) + "Register.php?name=" + user.getName()+"&lastName="+user.getLasName()+
                "&id=" + user.getId()+"&email="+user.getEmail()+"&password="+user.getPsw()+"&age="+user.getAge()+"&sex="+user.getSex()+
                "&phone=" + user.getPhone();
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    private Boolean validateRegister() {
        if (etUserName.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su nombre", Snackbar.LENGTH_SHORT).show();
            etUserName.setError("Ingrese su nombre");
            return false;
        } else if (etLastName.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese sus apellidos", Snackbar.LENGTH_SHORT).show();
            etLastName.setError("Ingrese sus apellidos");
            return false;
        } else if (etIdentification.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su identificación", Snackbar.LENGTH_SHORT).show();
            etIdentification.setError("Ingrese su identificación");
            return false;
        } else if (etEmail.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su correo electrónico", Snackbar.LENGTH_SHORT).show();
            etEmail.setError("Ingrese su correo electrónico");
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su contraseña", Snackbar.LENGTH_SHORT).show();
            etPassword.setError("Ingrese su contraseña");
            return false;
        } else if (etPasswordRepeat.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese de nuevo su contraseña", Snackbar.LENGTH_SHORT).show();
            etPasswordRepeat.setError("Ingrese de nuevo su contraseña");
            return false;
        } else if (!etPassword.getText().toString().equals(etPasswordRepeat.getText().toString())) {
            Snackbar.make(getView(), "Las contraseñas deben coincidir", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (etPassword.getText().toString().equals(etPasswordRepeat.getText().toString())) {
            if (etPassword.getText().toString().length() >= 6 && etPassword.getText().toString().length() <= 16) {
                if (etAge.getText().toString().isEmpty()) {
                    Snackbar.make(getView(), "Ingrese su edad", Snackbar.LENGTH_SHORT).show();
                    etAge.setError("Ingrese su edad");
                    return false;
                } else if (!rbtnMale.isChecked() && !rbtnFamale.isChecked()) {
                    Snackbar.make(getView(), "Ingrese su sexo", Snackbar.LENGTH_SHORT).show();
                    return false;
                } else if (etPhone.getText().toString().isEmpty()) {
                    Snackbar.make(getView(), "Ingrese su número telefónico", Snackbar.LENGTH_SHORT).show();
                    etPhone.setError("Ingrese su número telefónico");
                    return false;
                }else {
                    return true;
                }
            } else {
                Snackbar.make(getView(), "La contraseña debe tener minimo 6 y maximo 16 caracteres", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        } else {
            return true;
        }
    }

    private void goToLogin(){
        Intent intent = new Intent();
        activity.setResult(RESULT_OK,intent);
        activity.finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(activity, "No se pudo registrar "+ error.toString(),Toast.LENGTH_SHORT).show();
        //Log.d("NoRegister",dataToRegister.toString()+"\n"+error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            if (jsonObject.optString("success").equals("true") && jsonObject.optString("message").equals("registered")){
                //Snackbar.make(getView(), "El usuario ha sido registrado existosamente", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(activity,"El usuario ha sido registrado existosamente",Toast.LENGTH_SHORT).show();
                Log.d("RegisterSQL","El usuario ha sido registrado existosamente");
                //initDataBase();
                goToLogin();

            }else if (jsonObject.optString("success").equals("false")&& jsonObject.optString("message").equals("existent")){
                //Snackbar.make(getView(), "El usuario ya existe con este numero de cedula", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(activity,"El usuario ya existe con este numero de cedula", Toast.LENGTH_SHORT).show();
                Log.d("RegisterSQL","El usuario ya existe con este numero de cedula");
                goToLogin();
            }
            else if (jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("fail")) {
                //Snackbar.make(getView(), "Fallo la comunicacion", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(activity, "Fallo la comunicacion", Toast.LENGTH_SHORT).show();
                Log.d("RegisterSQL","Fallo la comunicacion");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean initDataBase() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(),user.getPsw())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("RegisterFIREBASE ",String.valueOf(task.isSuccessful()));
                        flagPrueba = false;
                        if (task.isSuccessful()){
                            Log.d("RegisterFIREBASE ",String.valueOf(task.isSuccessful()));
                            Usuario usuario = new Usuario();
                            usuario.setName(user.getName());
                            usuario.setEmail(user.getEmail());
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            DatabaseReference reference = database.getReference("User/"+currentUser.getUid());
                            reference.setValue(usuario);
                            FirebaseAuth.getInstance().signOut();
                            //Toast.makeText(activity,"El usuario ha sido registrado existosamente",Toast.LENGTH_SHORT).show();
                            loadWebService();
                            flagPrueba = true;
                            //goToLogin();
                        }else{
                            Log.d("SESION","Error en la cuenta");
                            flagPrueba = false;
                        }
                    }
                });
        return flagPrueba;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
