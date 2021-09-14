package com.example.hunminjungum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class OcrListView extends LinearLayout {
    TextView tv_contents;
    public OcrListView(Context context){
        super(context);
        init(context);
    }

    public OcrListView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_ocr_list,this, true);
        tv_contents=(TextView)findViewById(R.id.ocrtext);
    }
    public void setContents(String contents){
        tv_contents.setText(contents);
    }
}
