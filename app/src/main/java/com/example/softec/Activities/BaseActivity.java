package com.example.softec.Activities;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.input.InputManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.StaticClasses.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseActivity extends AppCompatActivity {

    SharedPreference sp;
    public StaticClass st;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new SharedPreference(this,"app_local_data");
        st = new StaticClass(this);
        gson = new Gson();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void callNumber(String number) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Utils.call_number_request_code);
            return;
        }
        startActivity(intent);
    }

    public void hideKeyboardFrom(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public boolean isKeyBoardActive(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public void openActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    public void openSideBar(int i) {
        Intent intent = new Intent(this, SideBarExtras.class);
        intent.putExtra("count", i);
        startActivity(intent);
    }

    public void shareApp() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Share App Link...");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }

    public void hoverEffect(View view) {

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.1f);
        fadeOut.setDuration(80);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1.0f);
        fadeIn.setDuration(80);
        animatorSet.play(fadeIn).after(fadeOut);
        animatorSet.start();

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                getCurrentLocation();
            }
            //st.toast("Enabled");

        }

    }
    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, You Have To Enable It...")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).show();

    }

    private static String longitude = "";
    private static String latitude = "";
    private static String distance_in_text = "";


    public void getCurrentLocation() {

        final LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);


                        LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {

                            int latestlocationIndex = locationResult.getLocations().size() - 1;

                            double lat = locationResult.getLocations().get(latestlocationIndex).getLatitude();
                            double lng = locationResult.getLocations().get(latestlocationIndex).getLongitude();

                            longitude = String.valueOf(lng);
                            latitude = String.valueOf(lat);

                            findDistance(lat, lng);

                        }

                    }
                }, Looper.myLooper());

    }

    public void stopShimmer(ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setShimmer(null);
    }

    public void startShimmer(ShimmerFrameLayout shimmerFrameLayout){
        shimmerFrameLayout.startShimmer();
    }

    public String findDistance(double lat,double lon) {

        if(!sp.containKey("latitude") || !sp.containKey("longitude")){

            return "Find Distance";
        }

        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(sp.getStringValue("latitude")));
        loc1.setLongitude(Double.parseDouble(sp.getStringValue("longitude")));

        Location loc = new Location("");
        loc.setLatitude(lat);
        loc.setLongitude(lon);

        float distance = loc.distanceTo(loc1);

        Double toBeTruncated = new Double((distance*0.001));

        Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return String.valueOf(truncatedDouble)+" KM";
    }

    public Spanned htmlText(String text){

        Spanned spanned = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            spanned = Html.fromHtml(text);
        }

        return spanned;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
