package com.example.softec.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.softec.MapMarkerData;
import com.example.softec.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeliverMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng lahore = new LatLng(31.5204, 74.3587);
        mMap.addMarker(new MarkerOptions().position(lahore).title("Lahore").draggable(true).flat(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lahore));
        selectLocationDragger();
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);

            addMultiple();

//            if (locationPermissionGranted) {
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(true);
////                mMap.getUiSettings().setAllGesturesEnabled(true);
//            } else {
//                mMap.setMyLocationEnabled(false);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                mMap.getUiSettings().setAllGesturesEnabled(true);
//                lastKnownLocation = null;
//                getLocationPermission();

//            }
        } catch (SecurityException e)  {
        }
    }


    private void selectLocationDragger(){

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng latLng = marker.getPosition();
                Geocoder geocoder = new Geocoder(DeliverMap.this, Locale.getDefault());
                try {
                    android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                    Toast.makeText(DeliverMap.this,address.toString(),Toast.LENGTH_LONG).show();
                    String text  = "";
                    String array[] = address.toString().split(",");
                    showAddress(address.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DeliverMap.this,"Try Again: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void showAddress(String text){

//        String text = "";

//        for(int i=0;i<array.length;i++){text = array[i]+"\n";}

        new AlertDialog.Builder(this).setMessage(text).setCancelable(true).show();

    }



    private void addMultiple(){

        List<MapMarkerData> list = new ArrayList<>();

        list.add(new MapMarkerData("73.0479","33.6844","Snippet","Islamabad"));
        list.add(new MapMarkerData("71.5249","34.0151","Snippet","Peshawar"));
        list.add(new MapMarkerData("66.9750","30.1798","Snippet","Quetta"));
        list.add(new MapMarkerData("67.0011","24.8607","Snippet","Karachi"));



        for(int i = 0 ; i < list.size() ; i++) {

            createMarker(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongtitude()),
                    list.get(i).getTitle());
        }


    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title));
    }

}
