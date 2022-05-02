package kr.co.mdaesin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.mdaesin.adapter.ReceptionQuantityAdapter;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class MainActivity extends AppCompatActivity {

    private ReceptionQuantityModelView receptionQuantityModelView;
    private List<ReceptionQuantityModelView> receptionQuantityModelViewList;
    private ReceptionQuantityAdapter receptionQuantityAdapter;
    private RecyclerView recyclerView;

    TextView textview_v1;
    private Calendar c;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.셋팅
        textview_v1 = (TextView) findViewById(R.id.textview_v1);
        textview_v1.setText(BasicUtils.getYesterdayDays().toString());
        textview_v1.setOnClickListener(onClickListener);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);

        CheckTypesTask task = new CheckTypesTask();
        task.execute();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜가 변경되면 목록 교체

                String tmpDate = year + "-" + BasicUtils.getFormatDate( (month+1) ) + "-" + BasicUtils.getFormatDate( dayOfMonth );
                String tmpDateDay = BasicUtils.getDayOfweek(tmpDate, Label.DELIVERY_STANDARD_DATE_FORMAT);
                textview_v1.setText( tmpDate + " ("+tmpDateDay +")");
            }
        }, mYear, mMonth, mDay);

        textview_v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textview_v1.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });
    }

    //산단 매뉴 활성화(...)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // 화면에 표기된  클릭 이벤트 처리
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.textview_v1:
                    Toast.makeText(MainActivity.this, Label.RECEIPT_CONSTRUCTOR_FINAL_NAME, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void receptionQuantityModelView(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    settingRceptionQuantityModelView();
                    if ( receptionQuantityModelViewList != null && receptionQuantityModelViewList.size() > 0 ){

                        recyclerView = findViewById(R.id.receipt_recyceler_view);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        receptionQuantityAdapter = new ReceptionQuantityAdapter(receptionQuantityModelViewList);
                        recyclerView.setAdapter(receptionQuantityAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }

            }
        });
    }

    // 임시로 사용 셋팅
    public void settingRceptionQuantityModelView(){

        receptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();

        for ( int i=0; i < 500; i ++){
            receptionQuantityModelView = new ReceptionQuantityModelView();
            receptionQuantityModelView.setLinecode("102003");
            receptionQuantityModelView.setLinename("부곡터→인천구월");
            receptionQuantityModelView.setCarcode("105829");
            receptionQuantityModelView.setCarname("서울90바5829");
            receptionQuantityModelView.setQty(1);
            receptionQuantityModelView.setCnt(10);
            receptionQuantityModelView.setChong("1,848.0");
            receptionQuantityModelView.setGugan("2,100.0");
            receptionQuantityModelViewList.add(receptionQuantityModelView);
        }
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                MainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("야 쫌만 기다려봐 ~ ");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                receptionQuantityModelView();
                for (int i = 0; i < 10; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}