package com.pcs.app.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pcs-03 on 17/12/15.
 */
public class DrawGeoJSON extends AsyncTask<Void,Void,List<LatLng>>{

    private static final String TAG = DrawGeoJSON.class.getSimpleName();

    private MapView mapView;
    private Context context;

    public DrawGeoJSON(MapView mapView, Context mainActivity) {
        this.mapView = mapView;
        this.context = mainActivity;
    }

    @Override
    protected List<LatLng> doInBackground(Void... params) {
        ArrayList<LatLng> points = new ArrayList<LatLng>();

        try {
            // Load GeoJSON file
            InputStream inputStream = context.getAssets().open("example.geojson");
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            inputStream.close();

            // Parse JSON
            JSONObject json = new JSONObject(sb.toString());
            JSONArray features = json.getJSONArray("features");
            JSONObject feature = features.getJSONObject(0);
            JSONObject geometry = feature.getJSONObject("geometry");
            if (geometry != null) {
                String type = geometry.getString("type");

                // Our GeoJSON only has one feature: a line string
                if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {

                    // Get the Coordinates
                    JSONArray coords = geometry.getJSONArray("coordinates");
                    for (int lc = 0; lc < coords.length(); lc++) {
                        JSONArray coord = coords.getJSONArray(lc);
                        LatLng latLng = new LatLng(coord.getDouble(1), coord.getDouble(0));
                        points.add(latLng);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception Loading GeoJSON: " + e.toString());
        }

        return points;
    }

    @Override
    protected void onPostExecute(List<LatLng> points) {
        if (points.size() > 0) {
            LatLng[] pointsArray = points.toArray(new LatLng[points.size()]);

            // Draw Points on MapView
            mapView.addPolyline(new PolylineOptions()
                    .add(pointsArray)
                    .color(Color.parseColor("#0000FF"))
                    .width(10));
        }
        super.onPostExecute(points);
    }
}
