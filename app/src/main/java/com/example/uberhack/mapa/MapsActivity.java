package com.example.uberhack.mapa;

import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int STORAGE_PERMISSION_CODE = 23;
    private LocationManager locationManager;
    private LocationListener listener;
    private TextToSpeech mTTS;
    public static int EARTH_RADIUS_KM = 6371;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.getDefault());

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {

                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CreatePoints();
        StartMonitoring();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            requestStoragePermission();
        }
    }


    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]
                {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.GET_ACCOUNTS,
                        android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_PHONE_STATE
                }, STORAGE_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Not Aut", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void CreatePoints(){

        //Add a hole in Faria Lima and move the camera
        LatLng holeFariaLima = new LatLng(-23.583930, -46.683527);
        mMap.addMarker(new MarkerOptions().position(holeFariaLima).title("Buraco na Faria Lima"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(holeFariaLima, 18));

        //Add a garbage in Faria Lima and move the camera
        LatLng gabageFariaLima = new LatLng(-23.584175, -46.683502);
        mMap.addMarker(new MarkerOptions().position(gabageFariaLima).title("Monte de lixo na Faria Lima"));

        //Add a bike in Faria Lima and move the camera
        LatLng bikeFariaLima = new LatLng(-23.584912, -46.682901);
        mMap.addMarker(new MarkerOptions().position(bikeFariaLima).title("Bicicleta parada na Faria Lima"));

        //Add a bike in Faria Lima and move the camera
        LatLng hubSP = new LatLng(-23.54915009, -46.7332942 );
        mMap.addMarker(new MarkerOptions().position(hubSP).title("Patinete Hub SP"));

    }

    public void StartMonitoring(){

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //textView.append("\n " + location.getLongitude() + " " + location.getLatitude());

                System.out.println("\n " + location.getLongitude() + " " + location.getLatitude());

                LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 19);
                mMap.animateCamera(yourLocation);

                double latHubSp = -23.54915009;
                double logHubSp = -46.7332942;


                double distancia = geoDistanceInKm(latHubSp,logHubSp, location.getLatitude(), location.getLongitude());

                System.out.println(distancia);

                Toast.makeText(getApplicationContext(), String.valueOf(distancia) , Toast.LENGTH_SHORT).show();

                if (distancia < 0.012){
                    speak();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

        mMap.setMyLocationEnabled(true);

       // map.setMyLocationEnabled(true);


    }


    private void speak() {
        String text = "Cuidado. Há patinéte na calçada";
        float pitch = 0.44f;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = 1.28f;
        if (speed < 0.1) speed = 0.1f;

        System.out.println("pith:" + pitch);
        System.out.println("speed:" + speed);

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    public static double geoDistanceInKm(double firstLatitude,
                                         double firstLongitude, double secondLatitude, double secondLongitude) {

        double firstLatToRad = Math.toRadians(firstLatitude);
        double secondLatToRad = Math.toRadians(secondLatitude);

        double deltaLongitudeInRad = Math.toRadians(secondLongitude
                - firstLongitude);

        return Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
                * Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
                * Math.sin(secondLatToRad))
                * EARTH_RADIUS_KM;
    }
}
