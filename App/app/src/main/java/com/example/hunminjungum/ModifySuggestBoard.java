package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ModifySuggestBoard extends Activity {
    int postnum1;
    String session_id;
    EditText et_title, et_content;
    TextView tt_writer, tt_date, tt_view;
    Button ok, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_board);

        Intent intent = getIntent();
        postnum1 = intent.getExtras().getInt("postnum");

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");

        et_title=(EditText)findViewById(R.id.txt_title);
        et_content=(EditText)findViewById(R.id.txt_content);
        tt_writer=(TextView)findViewById(R.id.txt_writer);
        tt_date=(TextView)findViewById(R.id.txt_date);
        tt_view=(TextView)findViewById(R.id.txt_view2);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        int view = jsonObject.getInt("view");
                        String date = jsonObject.getString("date");
                        String content = jsonObject.getString("content");
                        int postnum2 = jsonObject.getInt("postnum");

                        et_title.setText(title);
                        tt_writer.setText(id);
                        tt_date.setText(date);
                        tt_view.setText(String.valueOf(view));
                        et_content.setText(content);
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                    /*StringWriter sw = new StringWriter();
                     e.printStackTrace(new PrintWriter(sw));
                     String exceptionAsString = sw.toString();
                     Log.e("StackTrace", exceptionAsString);*/
                }
            }
        };
        ModifySuggestBoardRequest modifySuggestBoardRequestt = new ModifySuggestBoardRequest(postnum1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ModifySuggestBoard.this);
        queue.add(modifySuggestBoardRequestt);

        ok = (Button)findViewById(R.id.md_ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String sTitle = et_title.getText().toString();
                final String sContent = et_content.getText().toString();
                DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> responseListener = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        Toast.makeText(getApplicationContext(),"게시글 수정을 완료했습니다..",Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ModifySuggestBoard.this);
                                        builder.setMessage("게시글 수정을 완료했습니다.")
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                        Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                catch(Exception e) {
                                    e.printStackTrace();
                                    StringWriter sw = new StringWriter();
                                    e.printStackTrace(new PrintWriter(sw));
                                    String exceptionAsString = sw.toString();
                                    Log.e("StackTrace", exceptionAsString);
                                }
                            }
                        };
                        ModifySuggestBoardOkRequest modifySuggestBoardOkRequestt = new ModifySuggestBoardOkRequest(sTitle, sContent, postnum1,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ModifySuggestBoard.this);
                        queue.add(modifySuggestBoardOkRequestt);
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifySuggestBoard.this);
                builder.setMessage("게시글을 수정하시겠습니까?")
                        .setPositiveButton("확인", yesButtonClickListener)
                        .setNegativeButton("취소",null)
                        .create()
                        .show();
            }
        });


        cancel = (Button)findViewById(R.id.md_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
