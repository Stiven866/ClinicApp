package com.stivenduque.clinicapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    EditText etUserName, etLastName, etEmail, etIdentification, etPassword, etPasswordRepeat, etAge, etCivilState, etPhone;
    TextView tvGgoToLogin;
    Button btnRegister;
    String typeUserValue, sex;
    RadioButton rbtnMale, rbtnFamale;
    Map<String, String> dataToRegister;
    ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private DatabaseReference referenceUser;
    private FirebaseDatabase database;
    //private FirebaseAuth firebaseAuth;
    //private FirebaseAuth.AuthStateListener authStateListener;
    public PatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient, container, false);
        //initDataBase();
        initUi(view);
        initActions();
        //addItemsToSpinerTypeUser();
        //addItemsToSpinnerMedicReviewer();


        return view;
    }


    /*private void initDataBase() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null ){
                    Log.d("FirebaseUser", "Usuario logeado :)  : "+ firebaseUser.getEmail());
                }else {
                    Log.d("FirebaseUser", "No hay usuario logeado :(");
                }

            }
        };
    }*/


    private void initUi(View view) {
        etUserName = view.findViewById(R.id.et_register_names);
        etLastName = view.findViewById(R.id.et_register_lastnames);
        etEmail = view.findViewById(R.id.et_register_email);
        etIdentification = view.findViewById(R.id.et_register_identification);
        etPassword = view.findViewById(R.id.et_register_password);
        etPasswordRepeat = view.findViewById(R.id.et_register_password_repeat);
        etAge = view.findViewById(R.id.et_register_age);
        etCivilState = view.findViewById(R.id.et_register_civil_state);
        etPhone = view.findViewById(R.id.et_register_phone);
        rbtnFamale = view.findViewById(R.id.rbFamale);
        rbtnMale = view.findViewById(R.id.rbMale);

        tvGgoToLogin = view.findViewById(R.id.txv_goto_login);
        btnRegister = view.findViewById(R.id.btn_register);


        request = Volley.newRequestQueue(getContext());
        database = FirebaseDatabase.getInstance();

        referenceUser = database.getReference("User");
    }


    public void initActions() {
        tvGgoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        /*spTypeUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeUserValue = spTypeUser.getSelectedItem().toString();
                if (typeUserMap.get(typeUserValue).equals("medic")) {
                    etProfessionalId.setVisibility(View.VISIBLE);
                    spListMedicalCenters.setVisibility(View.VISIBLE);
                } else {
                    etProfessionalId.setVisibility(View.GONE);
                    spListMedicalCenters.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*spListMedicalCenters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                centerSelectedIndex = i;
                Log.d("JAHJAGSDASD:", String.valueOf(centerSelectedIndex));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    private void registerUser() {
        sexUser();
        if (validateRegister()) {
            dataToRegister = new HashMap<>();
            dataToRegister.put("userName", etUserName.getText().toString());
            dataToRegister.put("lastName", etLastName.getText().toString());
            //dataToRegister.put("typeUser", typeUserMap.get(typeUserValue));
            dataToRegister.put("identification", etIdentification.getText().toString());
            dataToRegister.put("email", etEmail.getText().toString());
            dataToRegister.put("password", etPassword.getText().toString());
            dataToRegister.put("age", etAge.getText().toString());
            //dataToRegister.put("civilState", etCivilState.getText().toString());
            dataToRegister.put("sex", sex);
            //dataToRegister.put("occupation", etOccupation.getText().toString());
            dataToRegister.put("phone", etPhone.getText().toString());
            //dataToRegister.put("professionalId", etProfessionalId.getText().toString());
            //dataToRegister.put("center", centersMap.get(String.valueOf(centerSelectedIndex)));
            Log.d("REGISTERING", dataToRegister.toString());
            //createAccount(dataToRegister);
            //Snackbar.make(findViewById(R.id.container_register), "Registro exitoso", Snackbar.LENGTH_SHORT).show();

            makeRequest();
            goToLogin();
        } else {

        }
    }

    /*private void createAccount(Map<String,String> Map) {
        firebaseAuth.createUserWithEmailAndPassword(Map.get("email"), Map.get("password")).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Snackbar.make(getView(), "Registro exitoso", Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(getView(), "Registro no valido", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
    private void sexUser() {
        if (rbtnFamale.isChecked()) {
            sex = "Mujer";
        } else if (rbtnMale.isChecked()) {
            sex = "Hombre";
        }
    }
    private void makeRequest() {

        loadWebService();

        try {

            //Intent intent = new Intent();
            //intent.putExtra("dataToRegister", (Serializable) dataToRegister);
            //setResult(RESULT_OK, intent);
        } catch (Exception error) {
            Log.d("onError", error.getMessage());
        }

    }

    private void loadWebService() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url = getResources().getString(R.string.url)+"Register.php?name="+dataToRegister.get("userName").toString()+
                "&lastName="+dataToRegister.get("lastName").toString()+"&id="+dataToRegister.get("identification").toString()+
                "&email="+dataToRegister.get("email").toString()+"&password="+dataToRegister.get("password").toString()+
                "&age="+dataToRegister.get("age").toString()+"&sex="+dataToRegister.get("sex").toString()+"&phone="+dataToRegister.get("phone").toString();
        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
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
        } else if (etAge.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su edad", Snackbar.LENGTH_SHORT).show();
            etAge.setError("Ingrese su edad");
            return false;
        } /*else if (etCivilState.getText().toString().equals("")) {
            Snackbar.make(getView(), "Ingrese su estado civil", Snackbar.LENGTH_SHORT).show();
            return false;
        }*/ else if (!rbtnMale.isChecked() && !rbtnFamale.isChecked()) {
            Snackbar.make(getView(), "Ingrese su sexo", Snackbar.LENGTH_SHORT).show();
            return false;
        } /*else if (etOccupation.getText().toString().equals("")) {
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su ocupación", Snackbar.LENGTH_SHORT).show();
            return false;
        }*/ else if (etPhone.getText().toString().isEmpty()) {
            Snackbar.make(getView(), "Ingrese su número telefónico", Snackbar.LENGTH_SHORT).show();
            etPhone.setError("Ingrese su número telefónico");
            return false;
        } /*else if (typeUserMap.get(typeUserValue).equals("medic") && etProfessionalId.getText().toString().equals("")) {
            Snackbar.make(findViewById(R.id.container_register), "Debe ingresar su identificación profesional", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (typeUserMap.get(typeUserValue).equals("medic") && centerSelectedIndex < 0) {
            Snackbar.make(findViewById(R.id.container_register), "Debe ingresar el centro médico al que pertenece", Snackbar.LENGTH_SHORT).show();
            return false;
        }*/ else {
            return true;
        }
    }
    private void goToLogin(){
        Intent intent = new Intent();
        getActivity().setResult(RESULT_OK,intent);
        getActivity().finish();
        //finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "No se pudo registrar "+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("NoRegister",dataToRegister.toString()+error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Se ha registrado correctamente",Toast.LENGTH_SHORT).show();
        User usuario = new User();
        usuario.setName(etUserName.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        referenceUser.push().setValue(usuario);
        progressDialog.hide();
        clearView();
        goToLogin();
    }

    /*private void addItemsToSpinerTypeUser(){
        List<String> listTypeUser = new ArrayList<String>();
        listTypeUser.add("Paciente");
        listTypeUser.add("Médico");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Register.this,R.layout.support_simple_spinner_dropdown_item, listTypeUser);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spTypeUser.setAdapter(dataAdapter);
    }
    private void addItemsToSpinnerMedicReviewer(){
        List<String>  localListCenters = new ArrayList<String>();
        localListCenters.add("SURA EPS");
        localListCenters.add("COMEVA");
        localListCenters.add("CAFÉ SALUD");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Register.this,R.layout.support_simple_spinner_dropdown_item,localListCenters);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spListMedicalCenters.setAdapter(dataAdapter);


    }*/
    private void clearView(){
        etUserName.setText("");
        etLastName.setText("");
        etIdentification.setText("");
        etPhone.setText("");
        etAge.setText("");
        etPasswordRepeat.setText("");
        etPassword.setText("");
        etEmail.setText("");
    }

}
