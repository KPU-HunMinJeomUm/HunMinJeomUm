package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteOcrRequest extends StringRequest {
    final static private String URL = "http://pianokim.cafe24.com/phpMyAdmin/deleteocr.php";
    private Map<String, String> map;

    public DeleteOcrRequest(int num, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("num", num+"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
