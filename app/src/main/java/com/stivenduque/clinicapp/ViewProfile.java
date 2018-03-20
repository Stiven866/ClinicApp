package com.stivenduque.clinicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Map;

public class ViewProfile extends AppCompatActivity {
    Map<String, String> dataUser;
    TextView tvUserName,tvIdUser, tvEmail, tvAgeUser, tvCivilState, tvUserOcupation, tvUserPhone, tvUserRole, tvSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvUserName = findViewById(R.id.tv_view_name_user);
        tvIdUser = findViewById(R.id.tv_view_id_user);
        tvEmail = findViewById(R.id.tv_view_email_user);
        tvAgeUser = findViewById(R.id.tv_view_age_user);
        tvCivilState = findViewById(R.id.tv_view_CS_user);
        tvUserOcupation = findViewById(R.id.tv_view_ocupation_user);
        tvUserPhone = findViewById(R.id.tv_view_phone_user);
        tvUserRole = findViewById(R.id.tv_view_role_user);
        tvSex = findViewById(R.id.tv_view_sex_user);

        Serializable extras = getIntent().getSerializableExtra("dataUser");
        dataUser = (Map<String, String>) extras;
        tvUserName.setText(dataUser.get("userName"));
        tvIdUser.setText(dataUser.get("identification"));
        tvEmail.setText(dataUser.get("email"));
        tvAgeUser.setText(dataUser.get("age"));
        tvCivilState.setText(dataUser.get("civilState"));
        tvUserOcupation.setText(dataUser.get("occupation"));
        tvUserRole.setText(dataUser.get("typeUser"));
        tvUserPhone.setText(dataUser.get("phone"));
        tvSex.setText(dataUser.get("sex"));


    }
    @Override
    public void onBackPressed() {
       setResult(RESULT_OK,new Intent());
       finish();
    }
}
