package kr.co.delivery_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapView;


//import com.google.android.gms.maps.MapView;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private Button details_success_btn;

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

        // 리스너가 몇개를 등록해야 하는거지.

        details_success_btn = (Button) findViewById(R.id.details_success_btn);
        details_success_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "배달 처리 시작 카메로 어플 실행 ㄱㄱ ", Toast.LENGTH_LONG).show();
            }
        });

    }




}