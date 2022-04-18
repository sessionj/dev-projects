package kr.co.delivery_v1.login;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.delivery_v1.comm.Label;

public class LoginRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_LOGIN;
    private Map<String, String> map;

    public LoginRequest(String phoneNumber, String agencyCode, String deliveryCourse, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        Log.d("==============>", "여기는 찍히는");
        map = new HashMap<>();
        map.put("param", phoneNumber+"\t"+agencyCode+"\t"+deliveryCourse);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
