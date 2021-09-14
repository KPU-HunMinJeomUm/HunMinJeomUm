package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ModifyActivity extends Activity {
    EditText edt_name, edt_email, edt_id, edt_passwd;
    Button ok, cancel;
    private AlertDialog dialog;
    String session_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        edt_name=(EditText)findViewById(R.id.et_name);
        edt_email=(EditText)findViewById(R.id.et_email);
        edt_id=(EditText)findViewById(R.id.et_id);
        edt_passwd=(EditText)findViewById(R.id.et_passwd);

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userID = jsonObject.getString("id");
                        String userPass = jsonObject.getString("passwd");
                        String username = jsonObject.getString("name");
                        String usermail = jsonObject.getString("mail");

                        edt_id.setText(userID);
                        edt_passwd.setText(userPass);
                        edt_name.setText(usermail);
                        edt_email.setText(username);
                        edt_id.setEnabled(false);
                        edt_name.setEnabled(false);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                        dialog = builder.setMessage("회원정보를 불러올 수 없습니다.").setNegativeButton("다시 시도", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ModifyReqeust modifyReqeust = new ModifyReqeust(session_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ModifyActivity.this);
        queue.add(modifyReqeust);

        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sEmail = edt_email.getText().toString();
                final String sPw = edt_passwd.getText().toString();
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
                                        Toast.makeText(getApplicationContext(),"회원 정보 수정을 완료했습니다.",Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                        builder.setMessage("회원 정보 수정을 완료했습니다.")
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        ModifyOkRequest modifyOkRequest = new ModifyOkRequest(session_id, sPw, sEmail, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ModifyActivity.this);
                        queue.add(modifyOkRequest);
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                builder.setMessage("회원정보를 수정하시겠습니까?")
                        .setPositiveButton("확인", yesButtonClickListener)
                        .setNegativeButton("취소",null)
                        .create()
                        .show();
            }
        });

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
