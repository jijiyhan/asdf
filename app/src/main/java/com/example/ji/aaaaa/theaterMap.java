package com.example.ji.aaaaa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class theaterMap extends Fragment implements OnMapReadyCallback {


    double L1;
    double L2;


    public theaterMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_theater_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        Bundle bundle = getArguments();
        L1 = bundle.getDouble("input1");
        L2 = bundle.getDouble("input2");

        L1= 37.123;
        L2 = 126.234234;

        LatLng U_LOCATION = new LatLng(L1, L2);
        MarkerOptions marker_user = new MarkerOptions();
        marker_user.position(U_LOCATION);
        marker_user.title("내 위치");
        //markerOptions.snippet("부연설명");
        Marker user_location = map.addMarker(marker_user);
        user_location.showInfoWindow();

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(U_LOCATION));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));
    }
}
