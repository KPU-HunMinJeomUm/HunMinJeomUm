package com.example.hunminjungum;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {
    final static private String URL = "http://pianokim.cafe24.com/phpMyAdmin/join.php";
    private Map<String, String> map;


    public JoinRequest(String id, String passwd, String mail, String name, Response.Listener<String> listener, Response.ErrorListener errListener) {

        super(Method.POST, URL, listener, errListener);

        map = new HashMap<>();
        map.put("id", id);
        map.put("passwd", passwd);
        map.put("mail", mail);
        map.put("name", name);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }


}

