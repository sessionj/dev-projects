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
     * ?????? ??????
     */
    private ProgressDialog progressDialog;
    private DeliveryDao deliveryDao;
    private DeliveryModelView deliveryModelView;
    private DeliveryModelView resultView;
    private ArrayList<Double> latAndLng;
    /**
     * ?????? ??????
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

// ????????? ??????

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
        actionBar.setTitle("?????????");
        actionBar.setDisplayHomeAsUpEnabled(true);
        getIntentValue();
        /**
         * ?????? ?????? ------------------------------- //
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
            resultView.setDelivery_state("????????????");
        } else {
            resultView.setDelivery_state("?????????");
        }
        details_delivery_course.setText(resultView.getDelivery_state() + "(" + resultView.getDeliverycourse() + ")");
        details_arrivalman_tel1.setText(resultView.getArrivalmantel());
        details_arrivalman_tel2.setText(resultView.getArrivalmantel2());

        //details_status.setText(resultView.getDelivery_state()== "N"?"?????????":"????????????" );
        details_createdate.setText(BasicUtils.getDataFormatConvert(resultView.getCreatdate(), Label.DELIVERY_STANDARD_DATE_FORMAT).toString());
        details_address.setText(resultView.getAdress().toString());
        details_arrival_name.setText(resultView.getArrivalman().toString());
        details_parts_and_packing.setText(resultView.getGoods().toString() + "(" + resultView.getPojang().toString() + ") | " + resultView.getQty() + "ea");
        details_parts_fare.setText("??? "+BasicUtils.addComma(resultView.getDeliveryfare()));

        /**
         * ?????? ?????? ------------------------------- //
         */

        /**
         * ?????? ?????? -------------------------------//
         */



        /**
         * ?????? ?????? -------------------------------//
         * ???????????? : https://github.com/oh3/Android_KakaoMAP-API_Maker/blob/master/app/src/main/java/com/example/kakaomap/MapActivity.java
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
            // ??? ?????? ????????? ????????? ?????? (???????????? ????????? - ?????? ?????? ????????? ????????????)
            if ( latAndLng != null || latAndLng.size() != 0){

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latAndLng.get(0), latAndLng.get(1)), true);
                MapPOIItem marker = new MapPOIItem();
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latAndLng.get(0), latAndLng.get(1));
                marker.setItemName(resultView.getArrivalman());
                marker.setTag(0);
                marker.setMapPoint(mapPoint);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // ???????????? ???????????? BluePin ?????? ??????.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // ????????? ???????????????, ???????????? ???????????? RedPin ?????? ??????.
                mapView.addPOIItem(marker);
            }
        }else{
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Label.DELIVERY_DEFAULT_POINT_LAT, Label.DELIVERY_DEFAULT_POINT_LNG), true);
        }

        /**
         * ????????? ?????? #########################################################################################
         */
        // ????????? ??????
        ivCapture = findViewById(R.id.ivCapture);   //ImageView ??????
        btnCamera = findViewById(R.id.btnCapture);  //Button ??????
        btnSave = findViewById(R.id.btnSave);       //Button ??????

        screenManipulationProcess();

        // ????????? ??????
        loadImgArr();
        //??????
        btnCamera.setOnClickListener(v -> captureCamera());
        //??????
        btnSave.setOnClickListener(v -> {
            try {
                BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //?????? ????????? ?????????
                if (bitmap == null) {
                    Toast.makeText(this, "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                } else {
                    //??????
                    Log.d(TAG, " bitmap : " + bitmap);
                    saveImg();
                    mCurrentPhotoPath = ""; //initialize
                }
                if (isFileSave){
                    // ?????? ????????? ?????? Local DB(room) Update
                    resultView.setDelivery_state("Y");
                    deliveryDao.isDeliveryStatusChange(resultView);

                    // ????????? ?????? ?????? ??????

                }

            } catch (Exception e) {
                Log.w(TAG, "SAVE ERROR!", e);
            }
        });
        /**
         * ????????? ?????? #########################################################################################
         */



    }

    // ?????? ??????
    private void screenManipulationProcess() {
        // ?????? ??????????????? ?????????????????? ???????????? ??????
        if ( !TextUtils.isEmpty(resultView.getDelivery_state())){
            btnSave.setVisibility(View.GONE);
        }
    }

    private void captureCamera() {
        // ?????? ?????? ?????? ??????????????? ????????? ?????? ?????????
        isFileSave = true;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d(TAG, " captureCamera");
        // ???????????? ?????? ??? ????????? ??????????????? ????????? ??????
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // ????????? ????????? ????????? ?????? ??????
            File photoFile = null;

            try {
                //????????? ????????? ??????????????? ????????? ???????????????
                File tempDir = getCacheDir();

                //?????????????????? ??????
                String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String imageFileName = "Capture_" + timeStamp + "_"; //ex) Capture_20201206_

                File tempImage = File.createTempFile(
                        imageFileName,                              /* ???????????? */
                        Label.DELIVERY_DELIVERY_PICTURE_EXTENSION,  /* ???????????? */
                        tempDir                                     /* ?????? */
                );

                // ACTION_VIEW ???????????? ????????? ?????? (??????????????? ??????)
                mCurrentPhotoPath = tempImage.getAbsolutePath();
                Log.d(TAG, " mCurrentPhotoPath : " + mCurrentPhotoPath);
                photoFile = tempImage;

            } catch (IOException e) {
                //?????? ????????? ????????? ???????????? ?????? ??????.
                Log.w(TAG, Label.DELIVERY_DELIVERY_PICTURE_FILE_CREATE_ERROR, e);
            }

            //????????? ??????????????? ?????????????????? ?????? ??????
            if (photoFile != null) {
                Log.d(TAG, " photoFile : " + photoFile);
                //Uri ????????????
                Uri photoURI = FileProvider.getUriForFile(this,
                        getPackageName() + Label.DELIVERY_DELIVERY_PICTURE_PROVIDER,
                        photoFile);
                //???????????? Uri??????

                Log.d(TAG, " photoURI : " + photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                //????????? ??????
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                // ?????? ??????
                ivCapture.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
            }
        }
    }

    //??????????????? ?????????
    private void saveImg() {

        try {
            //????????? ?????? ??????
            File storageDir = new File(getFilesDir() + Label.DELIVERY_DELIVERY_PICTURE_DIR);
            if (!storageDir.exists()) //????????? ????????? ??????.
                storageDir.mkdirs();

            //String filename = "????????????" + ".jpg";
            String filename = billNo+Label.DELIVERY_DELIVERY_PICTURE_EXTENSION;

            // ????????? ????????? ??????
            File file = new File(storageDir, filename);
            boolean deleted = file.delete();
            Log.w(TAG, "Delete Dup Check : " + deleted);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, output); //???????????? ????????? Compress
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

            //String filename = "????????????" + ".jpg";
            String filename = billNo+Label.DELIVERY_DELIVERY_PICTURE_EXTENSION;

            File file = new File(storageDir, filename);

            if ( file.isFile()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ivCapture.setImageBitmap(bitmap);
            }else{
                // ????????? ????????? ????????? ?????? ?????????
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

//                            //?????????????????? ?????? ????????? ??????????????? ??????
//                            BitmapFactory.Options options = new BitmapFactory.Options();
//                            options.inSampleSize = 8; //8?????? 1????????? ????????? ?????? ??????
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

                            //Rotate??? bitmap??? ImageView??? ??????
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

    //???????????? ?????? ????????? ????????????
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission(); //????????????
    }

    //?????? ??????
    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //????????? ????????? ?????? ??????
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "??? ?????? ???????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
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
                // ????????? ???????????? result ????????? ????????????.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
                    finish(); //????????? ????????? ??? ??????
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
        //??????
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog = new ProgressDialog(DeliveryDetailsActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("????????? ????????? ?????????");
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