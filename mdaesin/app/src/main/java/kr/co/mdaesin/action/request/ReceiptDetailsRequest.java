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
import kr.co.mdaesin.models.ReceptionQuantityModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=μνΈνν€		4001	DL	2022-04-07
 */
public class ReceiptDetailsRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public ReceiptDetailsRequest(ReceptionQuantityModelView receptionQuantityModelView, Response.Listener<String> successListener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("=============", "NETWOOK ERROR");
                    }
                });
        sb = new StringBuffer();
        map = new HashMap<>();

        sb.append(receptionQuantityModelView.getSearchMode());
        sb.append("\t"+CryptoKey.createCryptoKey(receptionQuantityModelView.getLinecode()));
        sb.append("\t"+receptionQuantityModelView.getLinecode());
        sb.append("\t"+receptionQuantityModelView.getSearchKeyword_date().replaceAll("-","").split(" ")[0]);
        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
