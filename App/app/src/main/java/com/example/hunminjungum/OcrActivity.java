package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class OcrActivity extends Activity {
    String session_id;
    ListView ocrlist;
    ocrAdpater adpater;
    int num, num_1;
    ArrayList<Integer> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocrlist);
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");
        ocrlist = (ListView)findViewById(R.id.ocrlist);
        adpater = new ocrAdpater();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            String contents = jsonObject.getString("contents");
                            num = jsonObject.getInt("num");
                            adpater.addContents(new OcrList(contents));
                            ocrlist.setAdapter(adpater);
                            array.add(i, num);
                            ocrlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    num_1 = array.get(position);
                                    DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if (success) {
                                                            Toast.makeText(getApplicationContext(), "삭제를 완료했습니다.", Toast.LENGTH_SHORT).show();
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(OcrActivity.this);
                                                            builder.setMessage("삭제를 완료했습니다.")
                                                                    .setPositiveButton("ok", null)
                                                                    .create();
                                                            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            String tag="asdf";
                                            DeleteOcrRequest deleteOcrRequest = new DeleteOcrRequest(num_1, responseListener2);
                                            Log.d(tag, String.valueOf(num_1));
                                            RequestQueue queue = Volley.newRequestQueue(OcrActivity.this);
                                            queue.add(deleteOcrRequest);
                                        }
                                    };
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(OcrActivity.this);
                                    builder1.setMessage("삭제하시겠습니까?")
                                            .setPositiveButton("확인", yesButtonClickListener)
                                            .create()
                                            .show();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        OcrRequest ocrRequest = new OcrRequest(session_id, responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(OcrActivity.this);
        queue1.add(ocrRequest);
    }

    class ocrAdpater extends BaseAdapter{
        ArrayList<OcrList> contents = new ArrayList<OcrList>();
        @Override
        public int getCount() {
            return contents.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void addContents(OcrList list){
            contents.add(list);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            OcrListView ocrListView = null;
            if(convertView==null){
                ocrListView = new OcrListView(getApplicationContext());
            }else{
                ocrListView = (OcrListView)convertView;
            }
            OcrList list=contents.get(position);
            ocrListView.setContents(list.getContents());
            return ocrListView;
        }
    }
}
