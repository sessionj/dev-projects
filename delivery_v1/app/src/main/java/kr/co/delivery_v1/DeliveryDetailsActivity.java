package kr.co.delivery_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapView;

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;


//import com.google.android.gms.maps.MapView;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private Button details_success_btn;
    private DeliveryDao deliveryDao;
    private DeliveryModelView deliveryModelView;
    private DeliveryModelView resultView;


    private TextView details_billno, details_createdate, details_address, details_user_info, details_parts_and_packing, details_delivery_course, details_status, details_arrival_name, details_arrivalman_tel1, details_arrivalman_tel2;

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
        details_billno = (TextView) findViewById(R.id.details_billno);
        details_delivery_course = (TextView) findViewById(R.id.details_delivery_course);
        details_createdate  = (TextView) findViewById(R.id.details_createdate);
        details_address = (TextView) findViewById(R.id.details_address);
        details_arrival_name  = (TextView) findViewById(R.id.details_arrival_name);
        details_arrivalman_tel1  = (TextView) findViewById(R.id.details_arrivalman_tel1);
        details_arrivalman_tel2  = (TextView) findViewById(R.id.details_arrivalman_tel2);
        details_parts_and_packing = (TextView) findViewById(R.id.details_parts_and_packing);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view) ;
        mapViewContainer.addView(mapView);

        deliveryDao = new DeliveryDao(this);
        deliveryModelView = new DeliveryModelView();
        deliveryModelView.setBillno(billNo);
        resultView = new DeliveryModelView();
        resultView = deliveryDao.getDeliveryArticle(deliveryModelView);

        details_billno.setText(BasicUtils.getDataFormatConvert(resultView.getBillno().toString()) );
        if ( resultView.getDelivery_state() != null && resultView.getDelivery_state().equals("Y")){
            resultView.setDelivery_state("배달완료");
        }else{
            resultView.setDelivery_state("배달중");
        }
        details_delivery_course.setText(resultView.getDelivery_state() + "("+resultView.getDeliverycourse()+")");


        details_arrivalman_tel1.setText(resultView.getArrivalmantel());
        details_arrivalman_tel2.setText(resultView.getArrivalmantel2());

        //details_status.setText(resultView.getDelivery_state()== "N"?"배달중":"배달완료" );
        details_createdate.setText(BasicUtils.getDataFormatConvert(resultView.getCreatdate(), Label.DELIVERY_STANDARD_DATE_FORMAT).toString());

        Log.d("arrman ", resultView.getArrivalman());
        Log.d("arrman t ", resultView.getArrivalmantel());

        details_address.setText(resultView.getAdress().toString());
        details_arrival_name.setText(resultView.getArrivalman().toString());
        details_parts_and_packing.setText(resultView.getGoods().toString()+"(" +resultView.getPojang().toString()+ ") | " + resultView.getQty()+"ea");

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

                // 카메라 어플 권한 설정 (manifeasts)

            }
        });
    }
}