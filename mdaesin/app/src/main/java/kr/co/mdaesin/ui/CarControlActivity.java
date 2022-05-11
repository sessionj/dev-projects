package kr.co.mdaesin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptCarAPIRequest;
import kr.co.mdaesin.action.request.ReceiptHistoryRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptDetailsUnsongAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class CarControlActivity extends AppCompatActivity {
    private String TAG = "CarControlActivity";
    WebView webView;
    ReceptionQuantityModelView receptionQuantityModelView;
    private Response.Listener<String> responseListener;
    private String carAPIKey;
    ProgressDialog asyncDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_control);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_carControl);

        // 차량번호, 날짜 전달 받기

        Intent intent = getIntent();
        receptionQuantityModelView = (ReceptionQuantityModelView) intent.getSerializableExtra("receptionQuantityModelView");

        // 차량번호
        Log.d(TAG, "onCreate: " + receptionQuantityModelView.getCarname());
        Log.d(TAG, "onCreate: " + receptionQuantityModelView.getSearchKeyword_date());

        webView = (WebView) findViewById(R.id.car_webview);
        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadWithOverviewMode(true);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
        webView.getSettings().setUseWideViewPort(true);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함
        webView.getSettings().setSupportZoom(false);  // 줌 설정 여부
        webView.getSettings().setBuiltInZoomControls(false);  // 줌 확대/축소 버튼 여부

        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부
        webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지 (localStorage) 사용여부

        webView.getSettings().setDefaultTextEncodingName("UTF-8");

        webView.loadUrl(Label.RECEIPT_CUSTOM_CALL+"서울90바5830");

        //webView.loadUrl("https://s1.u-vis.com/uvisc/SSOAction.do?method=viewGroupMap&AccessKey=d5c2661d2aaafc70a1b78d3348e4c8c9e5e024bd485a3040d206adf5260039f950ee75dd67e609d7a8a75eab55276f671f23df0efe0ab44acd471b972e95acf8");

        /*asyncDialog = new ProgressDialog(CarControlActivity.this);

        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("자료 확인중... ");
        asyncDialog.show();
        asyncDialog.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/

        /*runOnUiThread(new Runnable() {
              @Override
              public void run() {

                  try {
                      responseListener = new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {
                              Log.d(TAG, "onResponse: response : " + response.toString());
                              try {
                                  JSONArray resultarray = new JSONArray(response);

                                  if ( resultarray.length() > 0){
                                      for ( int i=0; i < resultarray.length(); i++){
                                          JSONObject Object = resultarray.getJSONObject(i);
                                          carAPIKey = Object.getString("AccessKey");
                                      }
                                  }

                              } catch (JSONException e) {
                                  e.printStackTrace();
                              } finally {
                                    // 웹뷰 호출
                                  Log.d(TAG, "url call :  " + Label.RECEIPT_CAR_MONITOR+carAPIKey);
                                  webView.loadUrl(Label.RECEIPT_CAR_MONITOR+carAPIKey);
                              }
                          }
                      };

                      ReceiptCarAPIRequest receiptCarAPIRequest = new ReceiptCarAPIRequest(receptionQuantityModelView, responseListener);
                      RequestQueue queue = Volley.newRequestQueue( CarControlActivity.this);
                      queue.add(receiptCarAPIRequest);

                  }catch (Exception e){

                  }finally {
                      asyncDialog.dismiss();
                      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                  }
              }
          });*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // API key 획득하기기
}