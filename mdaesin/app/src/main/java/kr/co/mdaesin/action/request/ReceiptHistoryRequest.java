package kr.co.mdaesin.action.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.mdaesin.comm.CryptoKey;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptHistoryModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=μνΈνν€		4001	DL	2022-04-07
 */
public class ReceiptHistoryRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public ReceiptHistoryRequest(ReceiptHistoryModelView receiptHistoryModelView, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("=============", "NETWOOK ERROR");
                    }
                });
        sb = new StringBuffer();
        map = new HashMap<>();
        sb.append(receiptHistoryModelView.getSearchMode());
        sb.append("\t"+CryptoKey.createCryptoKey(receiptHistoryModelView.getLinecode()));
        sb.append("\t"+receiptHistoryModelView.getLinecode());
        sb.append("\t"+receiptHistoryModelView.getSearchKeyword_date().replaceAll("-","").split(" ")[0]);
        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
