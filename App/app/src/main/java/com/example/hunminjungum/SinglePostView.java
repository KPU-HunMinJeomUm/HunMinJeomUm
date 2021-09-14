package com.example.hunminjungum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SinglePostView extends LinearLayout {
    TextView tv_title, tv_writer, tv_time, tv_views, tv_postnum;

    public SinglePostView(Context context){
        super(context);
        init(context);
    }

    public SinglePostView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_post_list,this, true);
        tv_title=(TextView)findViewById(R.id.title);
        tv_writer=(TextView)findViewById(R.id.writer);
        tv_time=(TextView)findViewById(R.id.time);
        tv_views=(TextView)findViewById(R.id.views);
        tv_postnum=(TextView)findViewById(R.id.postnum);
    }

    public void setTitle(String title){
        tv_title.setText(title);
    }
    public void setWriter(String writer){
        tv_writer.setText(writer);
    }
    public void setTime(String time){
        tv_time.setText(time);
    }
    public void setViews(int views){
        tv_views.setText(String.valueOf(views));
    }
    public void setPostnum(int postnum){
        tv_postnum.setText(String.valueOf(postnum));
    }
}
