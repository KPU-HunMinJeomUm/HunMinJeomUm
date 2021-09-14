package com.example.hunminjungum;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class LoadingActivity extends Dialog {
    public LoadingActivity(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);
    }
}
