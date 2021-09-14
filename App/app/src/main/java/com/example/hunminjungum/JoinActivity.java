package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class JoinActivity extends Activity {
    EditText et_name, et_email, et_id, et_passwd, et_passwdchk;
    Button id_ck, signup, cancel;
    private boolean validate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_name = (EditText)findViewById(R.id.name);
        et_email = (EditText)findViewById(R.id.email);
        et_id = (EditText)findViewById(R.id.id);
        et_passwd = (EditText)findViewById(R.id.passwd);
        et_passwdchk = (EditText)findViewById(R.id.passwd_check);
        id_ck = (Button)findViewById(R.id.id_check);
        signup = (Button)findViewById(R.id.signup);
        cancel = (Button)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        id_ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                if (validate) {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    builder.setMessage("아이디를 입력하세요.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                et_id.setEnabled(false);
                                validate = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                IdcheckRequest idcheckRequest = new IdcheckRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(idcheckRequest);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sName = et_name.getText().toString();
                final String sEmail = et_email.getText().toString();
                final String sPw = et_passwd.getText().toString();
                final String sId = et_id.getText().toString();
                String sPw_chk = et_passwdchk.getText().toString();
                if(sPw.equals(sPw_chk)){
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(),"회원 가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                    builder.setMessage("회원 가입을 축하합니다.")
                                            .setPositiveButton("ok", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(),"회원 가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                    builder.setMessage("회원 가입에 실패하였습니다.")
                                            .setNegativeButton("ok", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_SHORT).show();
                        }
                    };
                    JoinRequest joinRequest = new JoinRequest(sId,sPw,sEmail,sName, responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                    queue.add(joinRequest);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("ok", null)
                            .create()
                            .show();
                }
            }
        });
    }
}
