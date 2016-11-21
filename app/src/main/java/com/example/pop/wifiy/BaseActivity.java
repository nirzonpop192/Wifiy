package com.example.pop.wifiy;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pop on 11/22/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    abstract void  viewReference();
}
