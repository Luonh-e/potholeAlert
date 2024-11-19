package com.example.customer_mobile.ui.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customer_mobile.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapPage extends Fragment implements OnMapReadyCallback {

    private GoogleMap myMap;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_page, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Enable zoom controls (you can remove this if you have zoom buttons)
        myMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Da Nang (you can add more markers here)
        LatLng daNang = new LatLng(16.047079, 108.206230);
        myMap.addMarker(new MarkerOptions().position(daNang).title("Da Nang"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(daNang, 12.0f));

        // Zoom in/out buttons (if you have them in your layout)
        Button zoomInButton = requireView().findViewById(R.id.zoom_in_button);
        Button zoomOutButton = requireView().findViewById(R.id.zoom_out_button);

        zoomInButton.setOnClickListener(v -> myMap.animateCamera(CameraUpdateFactory.zoomIn()));
        zoomOutButton.setOnClickListener(v -> myMap.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    private void performSearch(String query) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());

                myMap.clear(); // Clear previous markers
                myMap.addMarker(new MarkerOptions().position(location).title(query));
                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
            } else {
                Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error searching for location", Toast.LENGTH_SHORT).show();
        }
    }
}