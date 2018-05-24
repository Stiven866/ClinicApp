package com.stivenduque.clinicapp.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stivenduque.clinicapp.R;

public class MapsPharmacies extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pharmacies);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate miCamara;

        // Add a marker in Sydney and move the camera
        LatLng pharm0 = new LatLng(6.2431894,-75.5790157);
        LatLng pharm1 = new LatLng(6.2670659,-75.579084);
        TenerMarker(pharm0,"Droguería Todo Drogas",0.9f,0.1f,0.1f);
        TenerMarker(pharm1,"Farmacia Pasteur",0.9f,0.1f,0.1f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pharm0));
        miCamara = CameraUpdateFactory.newLatLngZoom(new LatLng(6.2431894,-75.5790157), 9);
        mMap.animateCamera(miCamara);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Droguería Pasteur"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        //mMap.setMyLocationEnabled(true);
    }
    public void TenerMarker(LatLng latLng, String tilte, float opacidad, float dim1, float dim2){
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(tilte)
                .alpha(opacidad)
                .anchor(dim1, dim2)
                );

    }
}
