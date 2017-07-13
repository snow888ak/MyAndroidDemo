package com.example.rxjavademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button:
                intent.setClass(getApplicationContext(), DemoActivity1.class);
                break;
            case R.id.button2:
                intent.setClass(getApplicationContext(), DemoActivity2.class);
                break;
            case R.id.button3:
                intent.setClass(getApplicationContext(), DemoActivity3.class);
                break;
            case R.id.button4:
                intent.setClass(getApplicationContext(), DemoActivity4.class);
                break;
            case R.id.button5:
                intent.setClass(getApplicationContext(), DemoActivity5.class);
                break;
            case R.id.button6:
                intent.setClass(getApplicationContext(), DemoActivity6.class);
                break;
        }
        startActivity(intent);
    }
}
