package com.fussyvegan.scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ResortDetailActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapLocationAirportFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    TextView nameAirport;
    TextView infoAirport;
    TextView infLocation;

    MainActivity activity;


    private static final String NAME_LOCATION_AIRPORT = "name location airport";
    private static final String CODE_LOCATION_AIRPORT = "code location airport";
    private static final String PLACE = "place";

    private String nameLocationAirport;
    private String codeLocationAirport;
    private String place;


    public MapLocationAirportFragment() {
    }


    public static MapLocationAirportFragment newInstance(String param1, String param2) {
        MapLocationAirportFragment fragment = new MapLocationAirportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameLocationAirport = getArguments().getString(NAME_LOCATION_AIRPORT);
            codeLocationAirport = getArguments().getString(CODE_LOCATION_AIRPORT);
            place = getArguments().getString(PLACE);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_location, container, false);
        init(view, savedInstanceState);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init(View view, Bundle savedInstanceState){
        nameAirport = view.findViewById(R.id.nameAirport);
        nameAirport.setText(nameLocationAirport + " Airport "+ codeLocationAirport);
        infoAirport = view.findViewById(R.id.infoAirport);
        infoAirport.setText(nameLocationAirport + " Airport is a small regional airport");
        infLocation = view.findViewById(R.id.infoLocation);

        final List<Double> locationAirport = getLocationAirport();

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
//                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(locationAirport.get(0), locationAirport.get(1));
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").icon(getBitmap(activity, R.drawable.map_airport)));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private BitmapDescriptor getBitmap(Context context, int vectorID) {
        Drawable drawable = ContextCompat.getDrawable(context, vectorID);
        drawable.setBounds(0, 0, 72, 92);
        Bitmap bitmap = Bitmap.createBitmap(72, 92, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private List<Double> getLocationAirport(){

        List<Double> locationAirport = new ArrayList<>();
        if(Geocoder.isPresent()){
            try {
                String location = nameLocationAirport + " Airport";
                Geocoder gc = new Geocoder(activity);
                List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects
                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        Log.e("lat1", a.getLatitude() + "long: " + a.getLongitude());
                        locationAirport.add(a.getLatitude()); locationAirport.add(a.getLongitude());
                        return locationAirport;
                    }
                }
            } catch ( IOException e) {
                // handle the exception
            }
        }
        return locationAirport;
    }
}