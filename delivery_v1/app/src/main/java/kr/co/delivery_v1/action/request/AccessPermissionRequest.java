package kr.co.delivery_v1.action.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.delivery_v1.comm.CryptoKey;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.models.DeliveryModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키	4001	LO	1118211	abcd1234
 *
 */
public class AccessPermissionRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public AccessPermissionRequest(DeliveryModelView deliveryModelView, Response.Listener<String> listener, StringBuffer sbr) {

        super(Method.POST, URL, listener, null);
        sb = new StringBuffer();
        map = new HashMap<>();

        sb.append(CryptoKey.createCryptoKey(deliveryModelView.getArrivalagencycode())+"\t");
        sb.append(deliveryModelView.getArrivalagencycode()+"\t");
        sb.append(Label.DELIVERY_BASE_URL_DELIVERY_LIST+"\t");
        sb.append(deliveryModelView.getCreatdate().replaceAll("-", ""));

        Log.d("==================== param : ", deliveryModelView.getCreatdate());

        if(sbr != null && sbr.toString().length() > 0){
            sb.append("\t"+sbr.toString());
        }else{
            sb.append("\tAll");
        }
        Log.d(":: ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
