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
 *  * http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키		4001	DL	100	2022-04-07
 */
public class DeliveryRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public DeliveryRequest(DeliveryModelView deliveryModelView, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        sb = new StringBuffer();
        map = new HashMap<>();

        sb.append(CryptoKey.createCryptoKey(deliveryModelView.getArrivalagencycode())+"\t");
        sb.append(deliveryModelView.getArrivalagencycode()+"\t");
        sb.append(Label.DELIVERY_BASE_URL_DELIVERY_LIST+"\t");
        sb.append(deliveryModelView.getDeliverycourse()+"\t");
        sb.append(deliveryModelView.getInput_date());

        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
