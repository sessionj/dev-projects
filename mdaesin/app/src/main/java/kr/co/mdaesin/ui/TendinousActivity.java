package kr.co.mdaesin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptHistoryRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptHistoryAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.ui.popup.HistoryPopupActivity;

public class TendinousActivity extends AppCompatActivity {

    private String TAG = " :: ReceiptDetailsActivity.LOG";

    TextView tendinous_top_title;
    private RecyclerView recyclerView;
    private ReceptHistoryAdapter receptHistoryAdapter;
    private Response.Listener<String> responseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendinous);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_tendinous);

        tendinous_top_title = (TextView) findViewById(R.id.tendinous_top_title);


    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}