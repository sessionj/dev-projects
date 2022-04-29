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
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private DeliveryDao deliveryDao;
    private DeliveryModelView deliveryModelView;
    private DeliveryModelView resultView;
    private ArrayList<Double> latAndLng;
    /**
     * 화면 셋팅
     */
    private TextView details_billno, details_createdate, details_address, details_parts_and_packing, details_delivery_course,
            details_delivery_status, details_arrival_name, details_arrivalman_tel1, details_arrivalman_tel2, details_parts_fare;

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


    public static final int REQUEST_TAKE_PHOTO = 10;
    public static final int REQUEST_PERMISSION = 11;

    private Button btnCamera, btnSave;
    private ImageView ivCapture;
    private String mCurrentPhotoPath;
    private boolean isFileSave = true;

// 이벤트 처리

    @Override
    public void onBackPressed() {

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

        /**
         * 카메라 영역 #########################################################################################
         */
        // 카메라 셋팅
        ivCapture = findViewById(R.id.ivCapture);   //ImageView 선언
        btnCamera = findViewById(R.id.btnCapture);  //Button 선언
        btnSave = findViewById(R.id.btnSave);       //Button 선언

        screenManipulationProcess();

        // 이미지 로드
        loadImgArr();
        //촬영
        btnCamera.setOnClickListener(v -> captureCamera());
        //저장
        btnSave.setOnClickListener(v -> {
            try {
                BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //찍은 사진이 없으면
                if (bitmap == null) {
                    Toast.makeText(this, "저장할 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    //저장
                    Log.d(TAG, " bitmap : " + bitmap);
                    saveImg();
                    mCurrentPhotoPath = ""; //initialize
                }
                if (isFileSave){
                    // 파일 저장이 완료 Local DB(room) Update
                    resultView.setDelivery_state("Y");
                    deliveryDao.isDeliveryStatusChange(resultView);

                    // 본사로 완료 파일 전송

                }

            } catch (Exception e) {
                Log.w(TAG, "SAVE ERROR!", e);
            }
        });
        /**
         * 카메라 영역 #########################################################################################
         */



    }

    // 화면 조작
    private void screenManipulationProcess() {
        // 배달 완료처리가 되어있을경우 저장버튼 숨김
        if ( !TextUtils.isEmpty(resultView.getDelivery_state())){
            btnSave.setVisibility(View.GONE);
        }
    }

    private void captureCamera() {
        // 오류 이후 다시 실행할수도 있기에 다시 초기화
        isFileSave = true;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d(TAG, " captureCamera");
        // 인텐트를 처리 할 카메라 액티비티가 있는지 확인
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // 촬영한 사진을 저장할 파일 생성
            File photoFile = null;

            try {
                //임시로 사용할 파일이므로 경로는 캐시폴더로
                File tempDir = getCacheDir();

                //임시촬영파일 세팅
                String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String imageFileName = "Capture_" + timeStamp + "_"; //ex) Capture_20201206_

                File tempImage = File.createTempFile(
                        imageFileName,                              /* 파일이름 */
                        Label.DELIVERY_DELIVERY_PICTURE_EXTENSION,  /* 파일형식 */
                        tempDir                                     /* 경로 */
                );

                // ACTION_VIEW 인텐트를 사용할 경로 (임시파일의 경로)
                mCurrentPhotoPath = tempImage.getAbsolutePath();
                Log.d(TAG, " mCurrentPhotoPath : " + mCurrentPhotoPath);
                photoFile = tempImage;

            } catch (IOException e) {
                //에러 로그는 이렇게 관리하는 편이 좋다.
                Log.w(TAG, Label.DELIVERY_DELIVERY_PICTURE_FILE_CREATE_ERROR, e);
            }

            //파일이 정상적으로 생성되었다면 계속 진행
            if (photoFile != null) {
                Log.d(TAG, " photoFile : " + photoFile);
                //Uri 가져오기
                Uri photoURI = FileProvider.getUriForFile(this,
                        getPackageName() + Label.DELIVERY_DELIVERY_PICTURE_PROVIDER,
                        photoFile);
                //인텐트에 Uri담기

                Log.d(TAG, " photoURI : " + photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                //인텐트 실행
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                // 화면 로딩
                ivCapture.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
            }
        }
    }

    //이미지저장 메소드
    private void saveImg() {

        try {
            //저장할 파일 경로
            File storageDir = new File(getFilesDir() + Label.DELIVERY_DELIVERY_PICTURE_DIR);
            if (!storageDir.exists()) //폴더가 없으면 생성.
                storageDir.mkdirs();

            //String filename = "캡쳐파일" + ".jpg";
            String filename = billNo+Label.DELIVERY_DELIVERY_PICTURE_EXTENSION;

            // 기존에 있다면 삭제
            File file = new File(storageDir, filename);
            boolean deleted = file.delete();
            Log.w(TAG, "Delete Dup Check : " + deleted);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, output); //해상도에 맞추어 Compress
            } catch (FileNotFoundException e) {
                isFileSave = false;
                e.printStackTrace();
            } finally {
                try {
                    assert output != null;
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.e(TAG, "Captured Saved");
            Toast.makeText(this,  Label.DELIVERY_DELIVERY_PICTURE_SAVE_OK, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.w(TAG, "Capture Saving Error!", e);
            Toast.makeText(this, Label.DELIVERY_DELIVERY_PICTURE_SAVE_FAIL, Toast.LENGTH_SHORT).show();

        }
    }

    private void loadImgArr() {
        try {

            //File storageDir = new File(getFilesDir() + "/capture");
            File storageDir = new File(getFilesDir() + Label.DELIVERY_DELIVERY_PICTURE_DIR);

            //String filename = "캡쳐파일" + ".jpg";
            String filename = billNo+Label.DELIVERY_DELIVERY_PICTURE_EXTENSION;

            File file = new File(storageDir, filename);

            if ( file.isFile()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ivCapture.setImageBitmap(bitmap);
            }else{
                // 캡쳐된 내용이 없으면 영역 숨기기
                ivCapture.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.w(TAG, "Capture loading Error!", e);
            Toast.makeText(this, "load failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            //after capture
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {

                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));

                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

//                            //사진해상도가 너무 높으면 비트맵으로 로딩
//                            BitmapFactory.Options options = new BitmapFactory.Options();
//                            options.inSampleSize = 8; //8분의 1크기로 비트맵 객체 생성
//                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            Bitmap rotatedBitmap = null;
                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            //Rotate한 bitmap을 ImageView에 저장
                            ivCapture.setImageBitmap(rotatedBitmap);

                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            Log.w(TAG, "onActivityResult Error !", e);
        }
    }

    //카메라에 맞게 이미지 로테이션
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission(); //권한체크
    }

    //권한 확인
    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                    finish(); //권한이 없으면 앱 종료
                }
            }
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