package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends StringRequest {
    final static private String URL  = "http://pianokim.cafe24.com/phpMyAdmin/insertcomment.php";
    private Map<String, String> map;

    public CommentRequest(String id, int postnum, int boardid, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("postnum", postnum+"");
        map.put("boardid", boardid+"");
        map.put("content",content);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
