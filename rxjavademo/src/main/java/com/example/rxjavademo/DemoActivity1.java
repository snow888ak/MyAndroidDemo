package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.reactivestreams.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

                printMsgToTextView("observer onNext：" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                printMsgToTextView("observer onError");
            }

            @Override
            public void onComplete() {
                printMsgToTextView("observer onCompleted");
            }
        };
        /* 创建一个被观察者 */
        // OnSubscribe作用相当于一个计划表，当observable被订阅时OnSubscribe对象的call方法会被自动调用。
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                e.onNext("Hello");
                e.onNext("Hi");
                e.onNext("Aloha");
                e.onComplete();
            }
        });
        //订单观察者
        observable.subscribe(observer);
    }

    private void printMsgToTextView(String msg) {
        mTvContent.append(msg);
        mTvContent.append("\n");
    }

}
