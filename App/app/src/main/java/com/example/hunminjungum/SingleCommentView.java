package com.example.hunminjungum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SingleCommentView extends LinearLayout {
    TextView tv_writer, tv_comment;
    Button delete;
    public SingleCommentView(Context context){
        super(context);
        init(context);
    }

    public SingleCommentView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_comment, this, true);
        tv_writer = (TextView)findViewById(R.id.comment_user1);
        tv_comment = (TextView)findViewById(R.id.comment_user2);
        delete = (Button)findViewById(R.id.comment_delete);
    }

    public void setWriter(String writer){
        tv_writer.setText(writer);
    }
    public void setComment(String comment){
        tv_comment.setText(comment);
    }
    public void setDelete(){delete.setVisibility(View.VISIBLE);}
}
