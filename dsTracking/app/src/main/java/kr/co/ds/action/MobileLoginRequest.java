package kr.co.ds.action;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.ds.comm.CryptoKey;
import kr.co.ds.comm.Label;
import kr.co.ds.models.LoginModelView;
import kr.co.ds.models.TrackingModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키		4001	DL	2022-04-07
 */
public class MobileLoginRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_LOGIN;
    private Map<String, String> map;
    StringBuffer sb ;

    public MobileLoginRequest(LoginModelView model, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("=============", "NETWOOK ERROR");
                    }
                });
        sb = new StringBuffer();
        map = new HashMap<>();
        sb.append(model.getMode());
        sb.append("\t"+ CryptoKey.createCryptoKey(model.getPhone_number()));
        sb.append("\t"+model.getPhone_number());
        if ( model.getMode().equals(Label.DELIVERY_BASE_URL_MOBILE_LOGIN_GETKEY)){
            sb.append("\ttracking");
        }else{
            Log.d("", "MobileLoginRequest: " + model.getCertification_key());
            sb.append("\t"+model.getCertification_key());
        }

        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
