package kr.co.ds.action;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.ds.MainActivity;
import kr.co.ds.comm.CryptoKey;
import kr.co.ds.comm.Label;
import kr.co.ds.models.TrackingModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키		4001	DL	2022-04-07
 */
public class TrackingListRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_TRACKING;
    private Map<String, String> map;
    StringBuffer sb ;

    public TrackingListRequest(TrackingModelView model, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("=============", "NETWOOK ERROR");
                        MainActivity mainActivity = new MainActivity();
                        mainActivity.errorListener();
                    }
                });
        sb = new StringBuffer();
        map = new HashMap<>();
        sb.append(model.getSearchMode());
        sb.append("\t"+ CryptoKey.createCryptoKey(model.getArrivalmantel()));
        sb.append("\t"+model.getArrivalmantel());
        sb.append("\t"+model.getSearchType());

        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
