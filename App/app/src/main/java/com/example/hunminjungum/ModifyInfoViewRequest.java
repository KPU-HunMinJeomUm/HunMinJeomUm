package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyInfoViewRequest extends StringRequest {
    final static  private String URL="http://pianokim.cafe24.com/phpMyAdmin/modifyinfoview.php";
    private Map<String,String> map;

    public ModifyInfoViewRequest(int postnum, int view, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("postnum",postnum+"");
        map.put("view", view+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
