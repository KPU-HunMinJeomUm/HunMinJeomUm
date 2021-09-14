package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteSuggestBoardRequest extends StringRequest {
    final static private String URL = "http://pianokim.cafe24.com/phpMyAdmin/deletesuggestboard.php";
    private Map<String, String> map;
    public DeleteSuggestBoardRequest(int postnum, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("postnum", postnum+"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
