package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ViewFreeBoardRequest extends StringRequest {
    final static  private String URL="http://pianokim.cafe24.com/phpMyAdmin/viewfreeboard.php";
    private Map<String,String> map;

    public ViewFreeBoardRequest(Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);
        map=new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
