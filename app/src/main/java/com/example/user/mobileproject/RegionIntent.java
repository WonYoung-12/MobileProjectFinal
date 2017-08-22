package com.example.user.mobileproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 2016-06-05.
 */
public class RegionIntent extends AppCompatActivity implements OnMapReadyCallback {
    TextView name,add,type,gender;
    MapFragment mapFr;
    GoogleMap map;
    boolean flag =false;
    shelterClass sc;
    double latitude;
    double longtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_intent);
        init();
    }

    void init(){
        mapFr=(MapFragment)getFragmentManager().findFragmentById(R.id.map);  //프레그먼트 맵 찾아줌.
        mapFr.getMapAsync(this);
        name = (TextView)findViewById(R.id.region_name);
        add = (TextView)findViewById(R.id.region_add);
        type = (TextView)findViewById(R.id.region_type);
        gender = (TextView)findViewById(R.id.region_gender);

        Intent i = getIntent();
        sc= (shelterClass)i.getParcelableExtra("sc");
        name.setText(sc.name); add.setText(sc.address); type.setText(sc.type); gender.setText(sc.gender);
        searchPlace(sc.address);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        if(flag==true) {
            LatLng Loc = new LatLng(latitude, longtitude);
            MarkerOptions options = new MarkerOptions();
            options.position(Loc);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); // 	BitmapDescriptorFactory.fromResource(R.drawable.station))
            options.title(sc.name); //info window의 타이틀
            map.addMarker(options);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16));
        }else{
            Toast.makeText(this,"해당하는 주소를 못찾았습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    void searchPlace(String place){
        Geocoder gc=new Geocoder(this);
        String a=place;
        try{
            List<Address> addr=gc.getFromLocationName(place,5);
            if(addr.size()!=0){
                latitude=addr.get(0).getLatitude();
                longtitude=addr.get(0).getLongitude();
                flag=true;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
