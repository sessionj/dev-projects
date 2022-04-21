package kr.co.delivery_v1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.GpsTracker;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;


//import com.google.android.gms.maps.MapView;

public class DeliveryDetailsActivity extends AppCompatActivity {


    /**
     * 객체 셋팅
     */
    private Button details_success_btn;
    private DeliveryDao deliveryDao;
    private DeliveryModelView deliveryModelView;
    private DeliveryModelView resultView;
    private ArrayList<Double> latAndLng;
    /**
     * 화면 셋팅
     */
    private TextView details_billno
            , details_createdate
            , details_address

            , details_parts_and_packing
            , details_delivery_course
            , details_delivery_status
            , details_arrival_name
            , details_arrivalman_tel1
            , details_arrivalman_tel2;

    /**
     * GPS
     */
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private GpsTracker gpsTracker;
    private MapView mapView;
    private String billNo;
    private ViewGroup mapViewContainer;
    private String requestSearchDay ="";
// 이벤트 처리
    @Override
    public boolean onSupportNavigateUp()
    {
        Log.d("클릭", "클릭됨 ========================================= ");
        Intent intent = new Intent(DeliveryDetailsActivity.this, MainActivity.class);

        intent.putExtra("requestSearchDay", requestSearchDay);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(DeliveryDetailsActivity.this, MainActivity.class);
                intent.putExtra("requestSearchDay", requestSearchDay);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * getParam (Intent)
     */
    private void getIntentValue(){

        Intent intent = getIntent();
        requestSearchDay = intent.getStringExtra("requestSearchDay") == null ? "" : intent.getStringExtra("requestSearchDay");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("배달通");
        actionBar.setDisplayHomeAsUpEnabled(true);
        getIntentValue();
        /**
         * 화면 셋팅 ------------------------------- //
         */
        Intent intent = getIntent();
        billNo = intent.getStringExtra("billNo");

        if (billNo == null || billNo.equals("") || billNo.length() != 13) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        details_billno = (TextView) findViewById(R.id.details_billno);
        details_delivery_course = (TextView) findViewById(R.id.details_delivery_course);
        details_createdate = (TextView) findViewById(R.id.details_createdate);
        details_address = (TextView) findViewById(R.id.details_address);
        details_arrival_name = (TextView) findViewById(R.id.details_arrival_name);
        details_arrivalman_tel1 = (TextView) findViewById(R.id.details_arrivalman_tel1);
        details_arrivalman_tel2 = (TextView) findViewById(R.id.details_arrivalman_tel2);
        details_parts_and_packing = (TextView) findViewById(R.id.details_parts_and_packing);

        deliveryDao = new DeliveryDao(this);
        deliveryModelView = new DeliveryModelView();
        deliveryModelView.setBillno(billNo);
        resultView = new DeliveryModelView();
        resultView = deliveryDao.getDeliveryArticle(deliveryModelView);

        details_billno.setText(BasicUtils.getDataFormatConvert(resultView.getBillno().toString()));
        if (resultView.getDelivery_state() != null && resultView.getDelivery_state().equals("Y")) {
            resultView.setDelivery_state("배달완료");
        } else {
            resultView.setDelivery_state("배달중");
        }
        details_delivery_course.setText(resultView.getDelivery_state() + "(" + resultView.getDeliverycourse() + ")");
        details_arrivalman_tel1.setText(resultView.getArrivalmantel());
        details_arrivalman_tel2.setText(resultView.getArrivalmantel2());

        //details_status.setText(resultView.getDelivery_state()== "N"?"배달중":"배달완료" );
        details_createdate.setText(BasicUtils.getDataFormatConvert(resultView.getCreatdate(), Label.DELIVERY_STANDARD_DATE_FORMAT).toString());
        details_address.setText(resultView.getAdress().toString());
        details_arrival_name.setText(resultView.getArrivalman().toString());
        details_parts_and_packing.setText(resultView.getGoods().toString() + "(" + resultView.getPojang().toString() + ") | " + resultView.getQty() + "ea");

        /**
         * 화면 셋팅 ------------------------------- //
         */

        /**
         * 환경 체크 -------------------------------//
         */



        /**
         * 지도 셋팅 -------------------------------//
         * 참조문서 : https://github.com/oh3/Android_KakaoMAP-API_Maker/blob/master/app/src/main/java/com/example/kakaomap/MapActivity.java
         */
        latAndLng = new ArrayList<Double >();
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view) ;
        mapViewContainer.addView(mapView);
        mapView.setZoomLevel(3, true);
        mapView.zoomIn(true);
        mapView.zoomOut(true);

        if ( resultView.getAdress() != null && resultView.getAdress().length() > 0){
            latAndLng = BasicUtils.findGeoPoint(this, resultView.getAdress());
            // 앱 최초 실행시 중심점 변경 (청주우암 영업소 - 나의 혀재 위치로 해야하나)
            if ( latAndLng != null || latAndLng.size() != 0){

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latAndLng.get(0), latAndLng.get(1)), true);
                MapPOIItem marker = new MapPOIItem();
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latAndLng.get(0), latAndLng.get(1));
                marker.setItemName(resultView.getArrivalman());
                marker.setTag(0);
                marker.setMapPoint(mapPoint);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);
            }
        }else{
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Label.DELIVERY_DEFAULT_POINT_LAT, Label.DELIVERY_DEFAULT_POINT_LNG), true);
        }

        details_success_btn = (Button) findViewById(R.id.details_success_btn);
        details_success_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "배달 처리 시작 카메로 어플 실행 ㄱㄱ ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        mapViewContainer.clearFocus();
        super.finish();
    }

}