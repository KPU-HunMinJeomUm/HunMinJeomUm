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

public class WriteReportActivity extends Activity {
    EditText et_title, et_contents;
    String session_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button ok = (Button)findViewById(R.id.ok);
        et_title = (EditText)findViewById(R.id.et_title);
        et_contents = (EditText)findViewById(R.id.et_contents);
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String content = et_contents.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),"게시글 작성을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReportActivity.this);
                                builder.setMessage("게시글 작성을 완료하였습니다.")
                                        .setPositiveButton("ok", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(WriteReportActivity.this, CommunityActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"게시글 작성에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReportActivity.this);
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
                WriteReportRequest writeReportRequest = new WriteReportRequest(title, content, session_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteReportActivity.this);
                queue.add(writeReportRequest);
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
