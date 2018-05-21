package com.stivenduque.clinicapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stivenduque.clinicapp.R;
import com.stivenduque.clinicapp.Entidades.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAccountPatient extends Fragment implements
        AdapterView.OnItemSelectedListener, View.OnClickListener,  com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{
    private EditText etLastName, etName, etEmail;
    private Spinner spPaises, spLocalidades;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private String idPreferences;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account_patient, container, false);

        etLastName = view.findViewById(R.id.et_my_accoun_patient_lastnames);
        etName = view.findViewById(R.id.et_my_accoun_patient_name);
        etEmail = view.findViewById(R.id.et_my_accoun_patient_email);



        spPaises = view.findViewById(R.id.sp_my_accoun_patient_paises);
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(getContext(), R.array.account_paises, android.R.layout.simple_spinner_dropdown_item);
        adapterPaises.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spPaises.setAdapter(adapterPaises);

        spLocalidades = view.findViewById(R.id.sp_my_accoun_patient_localidad);
        ArrayAdapter<CharSequence> adapterLocalidades = ArrayAdapter.createFromResource(getContext(), R.array.account_localidades, android.R.layout.simple_spinner_dropdown_item);
        adapterLocalidades.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spLocalidades.setAdapter(adapterLocalidades);
        request = Volley.newRequestQueue(getContext());

        return view;
        //return inflater.inflate(R.layout.fragment_MyAccuntPatient, container, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sp_my_accoun_patient_paises:
                Toast.makeText(getContext(),"Estos son los paises ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp_my_accoun_patient_localidad:
                Toast.makeText(getContext(),"Estos son los Localidades",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No hay consulta "+ error.toString(),Toast.LENGTH_SHORT).show();
        Log.d("NoExiste",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        User user = new User();
        //Toast.makeText(this, "Message: "+ response,Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("User");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setName(jsonObject.optString("name"));
            user.setLasName(jsonObject.optString("lastName"));
            user.setEmail(jsonObject.optString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        etEmail.setText(user.getEmail());
        etLastName.setText(user.getLasName());
        etName.setText(user.getName());
        etName.setEnabled(false);
        etName.setFocusable(false);
        etLastName.setEnabled(false);
        etLastName.setFocusable(false);
        etEmail.setEnabled(false);
        etEmail.setFocusable(false);
    }
    private void loadPreferences(){
        sharedPreferences = this.getActivity().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        idPreferences = sharedPreferences.getString("id","0");
    }


    @Override
    public void onStart() {
        super.onStart();
        loadPreferences();
        //if (idPreferences != "0"){
        Log.d("User", "Usuario logueado");
        String url = getResources().getString(R.string.url)+ "Consulta.php?id="+idPreferences;
        //Log.d("User", url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
        //}

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
