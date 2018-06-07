package com.example.ji.aaaaa;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class theater3 extends Fragment {


    TextView textLatitude;
    TextView textLongitude;
    TextView uLocation;

    double latitude;
    double longitude;
    private Location myLocation = null;

    OnFragmentSendDouble fragmentSendDouble;
    public interface OnFragmentSendDouble{
        public void onSendDouble(double d1, double d2);
    }



    public theater3() {
        // Required empty public constructor
    }


    public static theater3 newInstance(double d1, double d2) {
        theater3 fragment = new theater3();
        Bundle bundle = new Bundle();
        bundle.putDouble("input1",d1);
        bundle.putDouble("input2",d2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theater3, container, false);

        textLatitude = (TextView) view.findViewById(R.id.latitude); //위도
        textLongitude = (TextView) view.findViewById(R.id.longitude); //경도
        uLocation = (TextView) view.findViewById(R.id.userLocation); // 내 위치

        startLocationService();
        //FragmentManager fragmentManager = getFragmentManager();
        //android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        //return inflater.inflate(R.layout.fragment_theater3, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            fragmentSendDouble = (OnFragmentSendDouble) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }





    private void startLocationService() {
        // 위치 관리자 객체 참조

        //LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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

            fragmentSendDouble.onSendDouble(latitude,longitude);


            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener", msg);

            textLatitude.setText("" + latitude);
            textLongitude.setText("" + longitude);
            GeocodingSercice(latitude,longitude);

            Toast.makeText(getActivity().getApplicationContext(), "위치 확인.", Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };


    private void GeocodingSercice(double d1, double d2) {

        //Geocoder geocoder = new Geocoder(this,Locale.KOREA);
        Geocoder geocoder = new Geocoder(this.getContext(),Locale.KOREA);

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