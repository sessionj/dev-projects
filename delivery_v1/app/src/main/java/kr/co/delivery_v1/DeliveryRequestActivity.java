package kr.co.delivery_v1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.delivery_v1.action.request.DeliveryRequest;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.db.delivery.AppDeliveryDatabase;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.login.LoginRequest;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.LoginModelView;

public class DeliveryRequestActivity extends AppCompatActivity {

    private TextView deliveryavt_date_picker_area, deliveryavt_delivery_cource, deliveryavt_agencycode;
    private TextView request_etc_btn;
    private Button request_btn;

    private String deliveryCourse;
    private String agencyCode;
    private DeliveryModelView deliveryModelView;
    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;

    private AppDeliveryDatabase appDeliveryDatabase;


    /**
     * 초기화 및 셋팅
     */
    private void init(){

        appDeliveryDatabase = AppDeliveryDatabase.getInstance(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryModelView = new DeliveryModelView();

        agencyCode = DeviceInfoUtil.getRoomSelecter(this,3);
        deliveryCourse = DeviceInfoUtil.getRoomSelecter(this,4);

        deliveryavt_agencycode = (TextView) findViewById(R.id.deliveryavt_agencycode);
        deliveryavt_delivery_cource = (TextView) findViewById(R.id.deliveryavt_delivery_cource);
        deliveryavt_date_picker_area = (TextView) findViewById(R.id.deliveryavt_date_picker_area);

        deliveryavt_agencycode.setText(agencyCode);
        deliveryavt_delivery_cource.setText(deliveryCourse);
        deliveryavt_date_picker_area.setText(BasicUtils.getDays("yyyy-MM-dd") + BasicUtils.getDayOfweek(BasicUtils.getDays("yyyy-MM-dd"), "yyyy-MM-dd"));

        request_etc_btn = (TextView) findViewById(R.id.request_etc_btn);    // 자료 더 가져오기(etc)
        request_btn = (Button) findViewById(R.id.request_btn);              // 클릭된 자료 가져오기

        deliveryModelView.setArrivalagencycode(agencyCode);
        deliveryModelView.setDeliverycourse(deliveryCourse);
        deliveryModelView.setCreatdate(BasicUtils.getDays("yyyy-MM-dd"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_request);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("배달通");
        actionBar.setDisplayHomeAsUpEnabled(true);

        init();

        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜가 변경되면 목록 교체
                String tmpDate = "";
                String tmpDateDay = "";
                tmpDate = year + "-" + BasicUtils.getFormatDate( (month+1) ) + "-" + BasicUtils.getFormatDate( dayOfMonth );
                tmpDateDay = BasicUtils.getDayOfweek(tmpDate, Label.DELIVERY_STANDARD_DATE_FORMAT);
                deliveryavt_date_picker_area.setText( tmpDate + " ("+tmpDateDay +")");
            }
        }, mYear, mMonth, mDay);

        deliveryavt_date_picker_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deliveryavt_date_picker_area.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });
        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
         */

        /**
         * date picker 변동
         */

        //deliveryavt_date_picker_area



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("", "request : ===================> start" );
                try{
                    roomDbRequest(response);

                }catch (Exception e){
                    Log.d("log ", e.toString());
                }
                Log.d("", "request : ===================> end" );
            }

            /**
             * roomdb insert
             * @param response
             */
            private void roomDbRequest(String response) {

                ArrayList<DeliveryModelView> resultData = new ArrayList<DeliveryModelView>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("rows");//배열의 이름

                    for ( int i=0; i < resultarray.length(); i++){
                        JSONObject Object = resultarray.getJSONObject(i);
                        deliveryModelView = new DeliveryModelView();
                        deliveryModelView.setBillno(Object.getString("billno"));
                        deliveryModelView.setInput_date(Object.getString("input_date"));
                        deliveryModelView.setInput_time(Object.getString("input_time"));
                        deliveryModelView.setTranscode(Object.getString("transcode"));
                        deliveryModelView.setSendingagencycode(Object.getString("sendingagencycode"));
                        deliveryModelView.setArrivalagencycode(Object.getString("arrivalagencycode"));
                        deliveryModelView.setSendingmantel(Object.getString("sendingmantel"));
                        deliveryModelView.setSendingman(Object.getString("sendingman"));
                        deliveryModelView.setArrivalmantel(Object.getString("arrivalmantel"));
                        deliveryModelView.setArrivalman(Object.getString("arrivalman"));
                        deliveryModelView.setZipcode(Object.getString("zipcode"));
                        deliveryModelView.setAdress(Object.getString("adress"));
                        deliveryModelView.setPrefare(Object.getString("prefare"));
                        deliveryModelView.setFare(Object.getString("fare"));
                        deliveryModelView.setDeliveryfare(Object.getString("deliveryfare"));
                        deliveryModelView.setOgideliveryfare(Object.getString("ogideliveryfare"));
                        deliveryModelView.setDistance(Object.getString("distance"));
                        deliveryModelView.setPayway(Object.getString("payway"));
                        deliveryModelView.setGoods(Object.getString("goods"));
                        deliveryModelView.setPojang(Object.getString("pojang"));
                        deliveryModelView.setQty(Object.getInt("qty"));
                        deliveryModelView.setWeight(Object.getString("weight"));
                        deliveryModelView.setMemo(Object.getString("memo"));
                        deliveryModelView.setBillstate(Object.getString("billstate"));
                        deliveryModelView.setDeliverycourse(Object.getString("deliverycourse"));
                        deliveryModelView.setCreatdate(Object.getString("creatdate"));

                        appDeliveryDatabase.basicDeliveryProcessDao().applicationData_insert(deliveryModelView);

                        /**
                         * 리스트로 넣는 방법을 알아야함 .
                         */
                        //resultData.add(deliveryModelView);
                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };

        DeliveryRequest deliveryRequest = new DeliveryRequest(deliveryModelView, responseListener );
        RequestQueue queue = Volley.newRequestQueue( DeliveryRequestActivity.this );
        queue.add( deliveryRequest );

    }

}