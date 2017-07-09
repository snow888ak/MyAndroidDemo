package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity2 extends AppCompatActivity {

    public static final String TAG = DemoActivity2.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        setTitle("通过just()创建事件列队");
    }

    public void startClick(View v){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                mTvContent.append("observer onCompleted");
                mTvContent.append("\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvContent.append("observer onError");
                mTvContent.append("\n");
            }

            @Override
            public void onNext(String s) {
                mTvContent.append("observer onNext：" + s);
                mTvContent.append("\n");
            }
        };
        //使用just()方法创建事件队列
        Observable.just("just_1", "just_2", "just_3").subscribe(observer);
        String[] fromArrays = {"from_1", "from_2", "from_3"};
        Observable.from(fromArrays).subscribe(observer);
        //just()与from创建事件队列的例子等同于使用create()方法创建事件队列
    }

}
