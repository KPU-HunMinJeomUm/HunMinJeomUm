package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class DeleteCommentRequest extends StringRequest {
    final static private String URL = "http://pianokim.cafe24.com/phpMyAdmin/deletecomment.php";
    private Map<String, String> map;
    public DeleteCommentRequest(int commentnum, String id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("id", id);
        map.put("commentnum", commentnum+"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
