package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteInfoActivity extends Activity {
    EditText t_title, t_contents;
    String session_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button b_ok = (Button)findViewById(R.id.info_ok);
        t_title = (EditText)findViewById(R.id.info_title);
        t_contents = (EditText)findViewById(R.id.info_contents);
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");
        b_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String if_title = t_title.getText().toString();
                String if_content = t_contents.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),"게시글 작성을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteInfoActivity.this);
                                builder.setMessage("게시글 작성을 완료하였습니다.")
                                        .setPositiveButton("ok", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(WriteInfoActivity.this, CommunityActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"게시글 작성에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteInfoActivity.this);
                                builder.setMessage("게시글 작성에 실패하였습니다.")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                WriteInfoRequest writeInfoRequest = new WriteInfoRequest(if_title, if_content, session_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteInfoActivity.this);
                queue.add(writeInfoRequest);
            }
        });
        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
