package kr.co.delivery_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapView;


//import com.google.android.gms.maps.MapView;

public class DeliveryDetailsActivity extends AppCompatActivity {

    //Intent intent = getIntent();
    private String billNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        Intent intent = getIntent();
        billNo = intent.getStringExtra("billNo");

        Toast.makeText(getApplicationContext(), "왔냐 : billno : "+ billNo , Toast.LENGTH_LONG).show();

        if ( billNo == null || billNo.equals("") || billNo.length() != 13){

            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view) ;
        mapViewContainer.addView(mapView);

        //리스너 등록
        /*mapView.setMapViewEventListener(get);
        mapView.setPOIItemEventListener(this);
        mapView.setOpenAPIKeyAuthenticationResultListener(this);*/



    }




}