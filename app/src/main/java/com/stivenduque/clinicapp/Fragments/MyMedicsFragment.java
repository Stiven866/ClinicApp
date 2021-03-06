package com.stivenduque.clinicapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stivenduque.clinicapp.Adapter.UserAdapter;
import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyMedicsFragment extends Fragment implements  com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    RecyclerView recyclerViewUser;
    ArrayList<User> userArrayList;

    ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private String idPreferences;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_medics_fragment, container, false);
        userArrayList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerViewUser = view.findViewById(R.id.rv_my_medics);
        recyclerViewUser.setLayoutManager(manager);
        recyclerViewUser.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());

        loadWebService();
        //return inflater.inflate(R.layout.my_medics_fragment, container, false);
        return view;
    }

    private void loadWebService() {
        //progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Consultando...");
        //progressDialog.show();
        loadPreferences();
        String url =  getResources().getString(R.string.url)+ "Consulta_medics.php?user_id="+idPreferences;
        Log.d("UURRLL",url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
     }
    private void loadPreferences(){
        sharedPreferences = this.getActivity().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        idPreferences = sharedPreferences.getString("id","0");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.toString().equals("com.android.volley.TimeoutError")){
            Toast.makeText(getContext(), "No tiene acceso a internet",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getContext(), "El usuario no existe "+ error.toString(),Toast.LENGTH_SHORT).show();
            Log.d("NoExiste",error.toString());
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        User user = null;
        JSONArray jsonArray = response.optJSONArray("Medics");
        try {
            for (int i = 0; i<jsonArray.length();i++){
                user = new  User();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("fail"))) {
                    if (!(jsonObject.optString("success").equals("false") && jsonObject.optString("message").equals("no_existent"))) {
                        user.setId(jsonObject.optString("id"));
                        user.setName(jsonObject.optString("name"));
                        user.setPhone(jsonObject.optString("phone"));
                        Log.d("2333", user.getName());
                        userArrayList.add(user);
                    }else{
                        Toast.makeText(getContext(),"No tiene medicos", Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
                }
                //progressDialog.hide();
            UserAdapter userAdapter  = new UserAdapter(userArrayList);
            recyclerViewUser.setAdapter(userAdapter);
            } catch (JSONException e) {
            e.printStackTrace();
            Log.d("soservices", "sos" + e.getMessage());
        }

    }
}
