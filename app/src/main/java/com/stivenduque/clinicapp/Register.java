package com.stivenduque.clinicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.stivenduque.clinicapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText etUserName, etLastName, etEmail, etIdentification, etPassword, etPasswordRepeat, etProfessionalId, etAge, etCivilState, etSex, etOccupation, etPhone;
    Spinner spTypeUser, spListMedicalCenters;
    TextView tvGgoToLogin;
    Map<String, String> typeUserMap;
    Map<String, String> centersMap;
    Button btnRegister;
    String typeUserValue;
    int centerSelectedIndex;
    Map<String,String> dataToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        initActions();
        addItemsToSpinerTypeUser();
        addItemsToSpinnerMedicReviewer();
    }

    private void initUi() {
        etUserName = findViewById(R.id.et_register_names);
        etLastName = findViewById(R.id.et_register_lastnames);
        etEmail = findViewById(R.id.et_register_email);
        etIdentification = findViewById(R.id.et_register_identification);
        etProfessionalId = findViewById(R.id.et_register_professionalid);
        etPassword = findViewById(R.id.et_register_password);
        etPasswordRepeat = findViewById(R.id.et_register_password_repeat);
        etAge = findViewById(R.id.et_register_age);
        etCivilState = findViewById(R.id.et_register_civil_state);
        etSex = findViewById(R.id.et_register_sex);
        etOccupation = findViewById(R.id.et_register_occupation);
        etPhone = findViewById(R.id.et_register_phone);

        spTypeUser = findViewById(R.id.register_spinner_type_user);
        spListMedicalCenters = findViewById(R.id.register_spinner_medical_center);
        tvGgoToLogin = findViewById(R.id.txv_goto_login);
        btnRegister = findViewById(R.id.btn_register);
        typeUserMap = new HashMap<>();
        typeUserMap.put("Médico", "medic");
        typeUserMap.put("Paciente", "patient");
        centersMap = new HashMap<>();
        centersMap.put("0","SURA EPS");
        centersMap.put("1","COMEVA");
        centersMap.put("2","CAFÉ SALUD");
    }

    public void initActions(){
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

        spTypeUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeUserValue = spTypeUser.getSelectedItem().toString();
                if(typeUserMap.get(typeUserValue).equals("medic")){
                    etProfessionalId.setVisibility(View.VISIBLE);
                    spListMedicalCenters.setVisibility(View.VISIBLE);
                }else{
                    etProfessionalId.setVisibility(View.INVISIBLE);
                    spListMedicalCenters.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spListMedicalCenters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                centerSelectedIndex = i;
                Log.d("JAHJAGSDASD:", String.valueOf(centerSelectedIndex));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void registerUser() {

        if(validateRegister()){
            dataToRegister = new HashMap<>();
            dataToRegister.put("userName", etUserName.getText().toString());
            dataToRegister.put("lastName", etLastName.getText().toString());
            dataToRegister.put("typeUser", typeUserMap.get(typeUserValue));
            dataToRegister.put("identification", etIdentification.getText().toString());
            dataToRegister.put("email", etEmail.getText().toString());
            dataToRegister.put("password", etPassword.getText().toString());
            dataToRegister.put("age", etAge.getText().toString());
            dataToRegister.put("civilState", etCivilState.getText().toString());
            dataToRegister.put("sex", etSex.getText().toString());
            dataToRegister.put("occupation", etOccupation.getText().toString());
            dataToRegister.put("phone", etPhone.getText().toString());
            dataToRegister.put("professionalId", etProfessionalId.getText().toString());
            dataToRegister.put("center", centersMap.get(String.valueOf(centerSelectedIndex)));
            Snackbar.make(findViewById(R.id.container_register), "Registro exitoso", Snackbar.LENGTH_SHORT).show();
            Log.d("REGISTERING", dataToRegister.toString());
            makeRequest();
        }else{

        }
    }

    private void makeRequest() {
        final ProgressDialog pd = ProgressDialog.show(this, "Registrando","Esperando respuesta" );
        try{
            pd.dismiss();
            Intent intent = new Intent();
            intent.putExtra("dataToRegister", (Serializable) dataToRegister);
            setResult(RESULT_OK,intent);
        }catch (Exception error){
            Log.d("onError", error.getMessage());
        }
    }


    private Boolean validateRegister(){
        if(etUserName.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su nombre", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etLastName.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese sus apellidos", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etIdentification.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su identificación", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etEmail.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su correo electrónico", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(etPassword.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su contraseña", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etPassword.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese de nuevo su contraseña", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(!etPassword.getText().toString().equals(etPasswordRepeat.getText().toString())){
            Snackbar.make(findViewById(R.id.container_register), "Las contraseñas deben coincidir", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etAge.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su edad", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etCivilState.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su estado civil", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etSex.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su sexo", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etOccupation.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su ocupación", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(etPhone.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Ingrese su número telefónico", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(typeUserMap.get(typeUserValue).equals("medic") && etProfessionalId.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.container_register), "Debe ingresar su identificación profesional", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(typeUserMap.get(typeUserValue).equals("medic") && centerSelectedIndex<0){
            Snackbar.make(findViewById(R.id.container_register), "Debe ingresar el centro médico al que pertenece", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    private void goToLogin(){
        finish();
    }

    private void addItemsToSpinerTypeUser(){
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


    }
}