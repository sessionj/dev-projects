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
import kr.co.mdaesin.models.ReceiptWayPointModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키		4001	DL	2022-04-07
 */
public class ReceiptWaypointRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public ReceiptWaypointRequest(ReceiptWayPointModelView receiptWayPointModelView, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        sb = new StringBuffer();
        map = new HashMap<>();

        sb.append(CryptoKey.createCryptoKey(receiptWayPointModelView.getLinecode()));
        sb.append("\t"+receiptWayPointModelView.getLinecode());
        sb.append("\t"+receiptWayPointModelView.getSearchMode());
        sb.append("\t"+receiptWayPointModelView.getSearchKeyword_date().replaceAll("-","").split(" ")[0]);

        if ( receiptWayPointModelView.getSearchMode().equals(Label.DELIVERY_BASE_URL_RECEIPT_WAYPOINT_DETAILS)){

            if(receiptWayPointModelView.getWaypoint().equals("1")){
                sb.append("\t"+receiptWayPointModelView.getStagencycode()+"\t1");
            }else {
                sb.append("\t"+receiptWayPointModelView.getEdagencycode()+"\t2");
            }
        }

        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
