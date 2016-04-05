package com.pcs.app.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private static final int PERMISSIONS_LOCATION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setStyle(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new LatLng(45.522585, -122.685699));
        mapView.setZoomEnabled(true);
        mapView.setZoomLevel(8,true);
        mapView.setMyLocationEnabled(false);
        // Show user location (purposely not in follow mode)
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
        } else {
            mapView.setMyLocationEnabled(true);
        }


//        drawPolygon();
//        addMarkers(mapView);
        new DrawGeoJSON(mapView,this).execute();

        mapView.onCreate(savedInstanceState);
    }


    private void drawPolygon() {

        ArrayList<LatLng> polygon = new ArrayList<>();

        polygon.add(new LatLng(45.522585, -122.685699));
        polygon.add(new LatLng(45.534611, -122.708873));
        polygon.add(new LatLng(45.530883, -122.678833));
        polygon.add(new LatLng(45.547115, -122.667503));
        polygon.add(new LatLng(45.530643, -122.660121));
        polygon.add(new LatLng(45.533529, -122.636260));
        polygon.add(new LatLng(45.521743, -122.659091));
        polygon.add(new LatLng(45.510677, -122.648792));
        polygon.add(new LatLng(45.515008, -122.664070));
        polygon.add(new LatLng(45.502496, -122.669048));
        polygon.add(new LatLng(45.515369, -122.678489));
        polygon.add(new LatLng(45.506346, -122.702007));
        polygon.add(new LatLng(45.522585, -122.685699));

        mapView.addPolygon(new PolygonOptions()
                .addAll(polygon)
                .fillColor(Color.parseColor("#3bb2d0")));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private Button changeButtonTypeface(Button button) {
        return button;
    }

    private void addMarkers(final MapView mapView) {
        mapView.addMarker(new MarkerOptions().title("Edinburgh").snippet("Scotland").position(new LatLng(55.94629, -3.20777)));
        mapView.addMarker(new MarkerOptions().title("Stockholm").snippet("Sweden").position(new LatLng(59.32995, 18.06461)));
        mapView.addMarker(new MarkerOptions().title("Prague").snippet("Czech Republic").position(new LatLng(50.08734, 14.42112)));
        mapView.addMarker(new MarkerOptions().title("Athens").snippet("Greece").position(new LatLng(37.97885, 23.71399)));
        mapView.addMarker(new MarkerOptions().title("Tokyo").snippet("Japan").position(new LatLng(35.70247, 139.71588)));
        mapView.addMarker(new MarkerOptions().title("Ayacucho").snippet("Peru").position(new LatLng(-13.16658, -74.21608)));
        mapView.addMarker(new MarkerOptions().title("Nairobi").snippet("Kenya").position(new LatLng(-1.26676, 36.83372)));
        mapView.addMarker(new MarkerOptions().title("Canberra").snippet("Australia").position(new LatLng(-35.30952, 149.12430)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.satellite:
                mapView.setStyle(Style.SATELLITE);
                return true;
            case R.id.dark:
                mapView.setStyle(Style.DARK);
                return true;
            case R.id.light:
                mapView.setStyle(Style.LIGHT);
                return true;
            case R.id.streets:
                mapView.setStyle(Style.MAPBOX_STREETS);
                return true;
            case R.id.emerald:
                mapView.setStyle(Style.EMERALD);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
}
