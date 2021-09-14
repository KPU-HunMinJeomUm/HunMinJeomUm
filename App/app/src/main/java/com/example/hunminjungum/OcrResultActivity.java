package com.example.hunminjungum;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import com.example.hunminjungum.BluetoothActivity;

public class OcrResultActivity extends AppCompatActivity implements TextPlayer, View.OnClickListener{
    private final Bundle params = new Bundle();
    private final BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.YELLOW);
    private TextToSpeech tts;
    private Button playBtn;
    private Button pauseBtn;
    private Button stopBtn;
    private Button zoomInBtn;
    private Button zoomOutBtn;
    private Button sendBtn;
    private TextView contentTextView;
    private PlayState playState = PlayState.STOP;
    private Spannable spannable;
    private int standbyIndex = 0;
    private int lastPlayIndex = 0;
    String ocrResult;
    String session_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result);
        setTitle("훈민점음");

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        ocrResult=intent.getStringExtra("ocr_result");
       /*if(Intent.ACTION_SEND.equals(action) && type!=null){
            if("text/plain".equals(type)){
                ocrResult = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        if(Intent.ACTION_SEND.equals(action) && type!=null){
            if("image/*".equals(type)){

            }
        }*/
        if(!session_id.equals("")&&!ocrResult.equals("null")&&!ocrResult.equals("첨부하신 파일에 텍스트가 존재하지 않습니다. \n")&&!ocrResult.equals("")) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Toast.makeText(getApplicationContext(),"텍스트를 저장하였습니다,",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"텍스트 저장에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            OcrOkRequest ocrOkRequest = new OcrOkRequest(session_id, ocrResult, responseListener);
            RequestQueue queue = Volley.newRequestQueue(OcrResultActivity.this);
            queue.add(ocrOkRequest);
        }
        initTTS();
        initView();


        sendBtn= (Button)findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                intent.putExtra("ocrresult",ocrResult);
                startActivity(intent);
            }
        });
    }

    public static int getMaxSpeechInputLength() {
        return 4000;
    }

    private void initView() {
        playBtn = findViewById(R.id.btn_play);
        pauseBtn = findViewById(R.id.btn_pause);
        stopBtn = findViewById(R.id.btn_stop);
        zoomInBtn = findViewById(R.id.btn_zoom_in);
        zoomOutBtn = findViewById(R.id.btn_zoom_out);
        contentTextView = findViewById(R.id.tv_content);

        contentTextView.setText(ocrResult);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        zoomInBtn.setOnClickListener(this);
        zoomOutBtn.setOnClickListener(this);
    }

    private void initTTS() {
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int state) {
                if (state == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                    tts.speak("완료되었습니다.", TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    showState("TTS 객체 초기화 중 문제가 발생했습니다.");
                }
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {
                clearAll();
            }

            @Override
            public void onError(String s) {
                showState(s);
            }

            @Override
            public void onRangeStart(String utteranceId, int start, int end, int frame) {
                Log.e("start", Integer.toString(start));
                Log.e("end", Integer.toString(end));
                Log.e("frame", Integer.toString(frame));
                changeHighlight(standbyIndex + start, standbyIndex + end);
                lastPlayIndex = start;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startPlay();
                break;

            case R.id.btn_pause:
                pausePlay();
                break;

            case R.id.btn_stop:
                stopPlay();
                break;

            case R.id.btn_zoom_in:
                zoomIn();
                break;

            case R.id.btn_zoom_out:
                zoomOut();
                break;
        }
        showState(playState.getState());
    }

    public void zoomIn(){
        float textSize = contentTextView.getTextSize();
        textSize += 10.0;
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }

    public void zoomOut(){
        float textSize = contentTextView.getTextSize();
        textSize -= 10.0;
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }

    @Override
    public void startPlay() {
        String content = ocrResult;
        if (playState.isStopping() && !tts.isSpeaking()) {
            setContent(content);
            startSpeak(content);
        } else if (playState.isWaiting()) {
            standbyIndex += lastPlayIndex;
            startSpeak(content.substring(standbyIndex));
        }
        playState = PlayState.PLAY;
    }

    @Override
    public void pausePlay() {
        if (playState.isPlaying()) {
            playState = PlayState.WAIT;
            tts.stop();
        }
    }

    @Override
    public void stopPlay() {
        tts.stop();
        clearAll();
    }

    private void changeHighlight(final int start, final int end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
    }

    private void setContent(String content) {
        contentTextView.setText(content, TextView.BufferType.SPANNABLE);
        spannable = (SpannableString) contentTextView.getText();
    }

    private void startSpeak(final String text) {

        int next = 3999;
        int pos =0;
        while(true) {
            String temp="";

            try {

                temp = text.substring(pos, next);
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, temp);
                tts.speak(temp, TextToSpeech.QUEUE_ADD, params);

                pos = pos + 3999;
                next = next + 3999;

            } catch (Exception e) {
                temp = text.substring(pos, text.length());
                tts.speak(temp, TextToSpeech.QUEUE_ADD, null);
                break;
            }

        }

        //tts.speak(text, TextToSpeech.QUEUE_ADD, params, text);
    };

    private void clearAll() {
        playState = PlayState.STOP;
        standbyIndex = 0;
        lastPlayIndex = 0;

        if (spannable != null) {
            changeHighlight(0, 0); // remove highlight
        }
    }

    private void showState(final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }, 0);
    }

    @Override
    protected void onPause() {
        if (playState.isPlaying()) {
            pausePlay();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (playState.isWaiting()) {
            startPlay();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tts.stop();
        tts.shutdown();
        super.onDestroy();
    }
}
