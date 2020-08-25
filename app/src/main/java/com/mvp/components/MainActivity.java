package com.mvp.components;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mvp.components.mvp.view.MyActivity;
import com.mvp.components.mvp.view.MyMultipleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View tv_open_my_activity = $(R.id.tv_open_my_activity);
        tv_open_my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyActivity.launch(view.getContext());
            }
        });
        View tv_open_my_multiple_activity = $(R.id.tv_open_my_multiple_activity);
        tv_open_my_multiple_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMultipleActivity.launch(view.getContext());
            }
        });

    }

    protected <T extends View> T $(int viewId) {
        return findViewById(viewId);
    }
}
