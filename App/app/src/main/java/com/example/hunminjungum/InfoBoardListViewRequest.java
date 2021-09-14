package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InfoBoardListViewRequest extends StringRequest {
    final static private String URL  = "http://pianokim.cafe24.com/phpMyAdmin/infoboardlistview.php";
    private Map<String, String> map;

    public InfoBoardListViewRequest(int postnum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("postnum", postnum+"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
