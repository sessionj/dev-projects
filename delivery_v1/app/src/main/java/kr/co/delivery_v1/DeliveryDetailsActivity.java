package kr.co.delivery_v1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PrimitiveIterator;

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.GpsTracker;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;


//import com.google.android.gms.maps.MapView;

public class DeliveryDetailsActivity extends AppCompatActivity {

    final private static String TAG = "PhotoActivity ";
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    /**
     * 객체 셋팅
     */
    private ProgressDialog progressDialog;
    private Button details_success_btn;
    private DeliveryDao deliveryDao;
    private DeliveryModelView deliveryModelView;
    private DeliveryModelView resultView;
    private ArrayList<Double> latAndLng;
    /**
     * 화면 셋팅
     */
    private TextView details_billno, details_createdate, details_address, details_parts_and_packing, details_delivery_course,
            details_delivery_status, details_arrival_name, details_arrivalman_tel1, details_arrivalman_tel2, details_parts_fare;

    final static int TAKE_PICTURE = 1;
    final static int REQUEST_TAKE_PHOTO = 1;
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

    private ImageView delivery_photo;
    private Button details_success_upload_btn;
    private boolean cameraPermission = false;

// 이벤트 처리

    @Override
    public void onBackPressed() {
        // 키 두번 누르면 종료(2초 안에)
        /*long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * getParam (Intent)
     */
    private void getIntentValue(){

        Intent intent = getIntent();
        requestSearchDay = intent.getStringExtra("requestSearchDay") == null ? "" : intent.getStringExtra("requestSearchDay");
        Log.d("==========> detail : ", requestSearchDay);
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
        details_parts_fare = (TextView) findViewById(R.id.details_parts_fare);


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
        details_parts_fare.setText("￦ "+BasicUtils.addComma(resultView.getDeliveryfare()));

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
        delivery_photo = (ImageView) findViewById(R.id.delivery_photo);
        details_success_upload_btn = (Button) findViewById(R.id.details_success_upload_btn);
        details_success_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(), "배달 처리 시작 카메로 어플 실행 ㄱㄱ ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

                /**
                 * 카메라 권한 실행
                 */

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        cameraPermission = true;
                        Log.d(TAG, "권한 설정 완료");
                    } else {
                        Log.d(TAG, "권한 설정 요청");
                        ActivityCompat.requestPermissions(DeliveryDetailsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

                if ( cameraPermission) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }


                /*details_success_btn.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.details_success_btn:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, TAKE_PICTURE);
                                break;
                        }
                    }
                });*/

            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            cameraPermission = true;
        }
    }

    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        delivery_photo.setVisibility(View.VISIBLE);
        details_success_upload_btn.setVisibility(View.VISIBLE);
        Log.d("tab ", "onActivityResult, requestCode : " + requestCode);

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                // 카메라가 실행된후 찍지 않고 뒤로가기 버튼을 클릭해도 requestCode 가 1 이 들어온다.

                Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                if (bitmap != null) {
                    delivery_photo.setImageBitmap(bitmap);
                }
                break;
            default:
                Bitmap tmpBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.tmp_img);
                delivery_photo.setImageBitmap(tmpBitMap);
                break;
        }
    }

    @Override
    public void finish() {
        Log.d(TAG," .finish");
        mapViewContainer.clearFocus();
        super.finish();
    }


    public void loading() {
        //로딩
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog = new ProgressDialog(DeliveryDetailsActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("잠시만 기다려 주세요");
                        progressDialog.show();
                    }
                }, 0);
    }

    public void loadingEnd() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 0);
    }
}