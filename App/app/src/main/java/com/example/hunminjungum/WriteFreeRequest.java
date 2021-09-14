package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class WriteFreeRequest extends StringRequest {
    final static private String URL  = "http://pianokim.cafe24.com/phpMyAdmin/insertfreeboard.php";
    private Map<String, String> map;

    public WriteFreeRequest(String title, String content, String id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("id", id);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
