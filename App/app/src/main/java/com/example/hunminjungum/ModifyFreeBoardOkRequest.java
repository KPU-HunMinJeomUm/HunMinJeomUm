package com.example.hunminjungum;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ModifyFreeBoardOkRequest extends StringRequest {
    final static  private String URL="http://pianokim.cafe24.com/phpMyAdmin/modifyfreeboard.php";
    private Map<String,String> map;

    public ModifyFreeBoardOkRequest(String title, String content, int postnum, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("title",title);
        map.put("content",content);
        map.put("postnum", postnum+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
