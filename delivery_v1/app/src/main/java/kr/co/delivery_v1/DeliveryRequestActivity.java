package kr.co.delivery_v1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.AppDatabase;

public class DeliveryRequestActivity extends AppCompatActivity {

    TextView deliveryavt_date_picker_area, deliveryavt_delivery_cource, deliveryavt_agencycode;
    TextView request_etc_btn;
    private Button request_btn;

    private String deliveryCourse;
    private String agencyCode;

    Calendar c;
    int mYear;
    int mMonth;
    int mDay;

    /**
     * 초기화 및 셋팅
     */
    private void init(){

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

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

    }


}