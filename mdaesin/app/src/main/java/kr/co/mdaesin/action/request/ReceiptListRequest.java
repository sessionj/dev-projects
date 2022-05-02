package kr.co.mdaesin.action.request;

import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.co.mdaesin.comm.CryptoKey;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

/**
 *   http://dev.ds3211.co.kr/DsService_AppInterlockProxy?param=암호화키		4001	DL	2022-04-07
 */
public class ReceiptListRequest extends StringRequest {

    final static private String URL = Label.DELIVERY_BASE_URL+Label.DELIVERY_BASE_URL_SUB_1;
    private Map<String, String> map;
    StringBuffer sb ;

    public ReceiptListRequest(ReceptionQuantityModelView receptionQuantityModelView, Response.Listener<String> listener) {

        super(Request.Method.POST, URL, listener, null);
        sb = new StringBuffer();
        map = new HashMap<>();

        sb.append(CryptoKey.createCryptoKey(receptionQuantityModelView.getLinecode())+"\t");
        sb.append(receptionQuantityModelView.getLinecode()+"\t");
        sb.append(Label.DELIVERY_BASE_URL_RECEIPT_LIST+"\t");
        sb.append(receptionQuantityModelView.getSearchKeyword_date().replaceAll("-",""));
        Log.d("=============param : ", sb.toString());
        map.put("param", sb.toString());
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
