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

public class DemoActivity1 extends AppCompatActivity {

    public static final String TAG = DemoActivity1.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        setTitle("使用create()创建事件序列");
    }

    public void startClick(View v){
        /* 1.创建一个观察者 */
        //创建一个观察者对象
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
        //Subscriber是Observer的实现类
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mTvContent.append("subscriber onCompleted");
                mTvContent.append("\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "subscriber onError");
                mTvContent.append("subscriber onError");
                mTvContent.append("\n");
            }

            @Override
            public void onNext(String s) {
                mTvContent.append("subscriber onNext " + s);
                mTvContent.append("\n");
            }
        };
        /* 创建一个被观察者 */
        // OnSubscribe作用相当于一个计划表，当observable被订阅时OnSubscribe对象的call方法会被自动调用。
        Observable observable = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        //订单观察者
        observable.subscribe(observer);
        observable.subscribe(subscriber);
    }

}
