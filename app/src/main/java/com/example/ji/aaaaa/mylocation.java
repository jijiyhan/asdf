package com.example.ji.aaaaa;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class mylocation extends AppCompatActivity implements OnMapReadyCallback {

    TextView textLatitude;
    TextView textLongitude;
    TextView uLocation;

    double latitude;
    double longitude;
    private Location myLocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylocation);

        textLatitude = (TextView) findViewById(R.id.latitude); //위도
        textLongitude = (TextView) findViewById(R.id.longitude); //경도
        uLocation = (TextView) findViewById(R.id.userLocation); // 내 위치

        startLocationService();

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.4943344, 126.9159406);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");

        Marker seoul = map.addMarker(markerOptions);
        seoul.showInfoWindow();

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


    private void startLocationService() {
        // 위치 관리자 객체 참조

        LocationManager  manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // 위치 정보를 받을 리스너 생성

        long minTime = 10000; //최소 시간 간격 (miliSecond)
        float minDistance = 10; //최소 변경거리 (m)

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);

            textLatitude.setText("위치정보 수신중...");
            textLongitude.setText("위치정보 수신중...");

            /*
            // 위치 확인이 안되는 경우 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();

                myLocation = lastLocation;
                textLatitude.setText("" + latitude);
                textLongitude.setText("" + longitude);
                Toast.makeText(getApplicationContext(), "최근 위치 확인.", Toast.LENGTH_SHORT).show();
            } else { //lastLocation ==null
                textLatitude.setText("위치정보 미수신");
                textLongitude.setText("위치정보 미수신");
                Toast.makeText(getApplicationContext(), "GPS를 켜주세요.", Toast.LENGTH_LONG).show();
            }
            */
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

    }

    private final LocationListener gpsListener = new LocationListener() {
        /**
         * 위치 정보가 확인될 때 자동 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            myLocation = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            //Log.i("GPSListener", msg);

            textLatitude.setText("" + latitude);
            textLongitude.setText("" + longitude);
            GeocodingSercice(latitude,longitude);

            Toast.makeText(getApplicationContext(), "위치 확인.", Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };


    private void GeocodingSercice(double d1, double d2) {

        Geocoder geocoder = new Geocoder(this, Locale.KOREA);

        List<Address> list = null;
        StringBuffer mAddress = new StringBuffer();

        latitude = myLocation.getLatitude();
        longitude = myLocation.getLongitude();
        try {
            list = geocoder.getFromLocation(d1,d2, 1); // 위도, 경도, 얻어올 값의 개수
        }
        catch (NumberFormatException e) { e.printStackTrace(); }
        catch (IOException e) { uLocation.setText("입출력 오류 발생 : " + e.getMessage()); e.printStackTrace(); }


        if( (list != null) && (list.size()>0) )
        {

            for(Address addr: list)
            {
                int index = addr.getMaxAddressLineIndex();
                for(int i=0;i<=index;i++){
                    mAddress.append(addr.getAddressLine(i));
                    mAddress.append(" ");
                }
                mAddress.append("\n");
            }
                uLocation.setText(mAddress);

        }
        else
            uLocation.setText("");

        uLocation.setText(list.get(0).getAddressLine(0).toString());
    }


}