package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity8 extends AppCompatActivity {

    public static final String TAG = DemoActivity8.class.getSimpleName();

    @BindView(R.id.content1)
    TextView mTvContent1;
    @BindView(R.id.content2)
    TextView mTvContent2;

    @BindView(R.id.btn_start1)
    Button mBtnStart1;
    @BindView(R.id.btn_start2)
    Button mBtnStart2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo8);
        ButterKnife.bind(this);
        mTvContent1.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent2.setMovementMethod(ScrollingMovementMethod.getInstance());
        setTitle("监听按钮点击次数");

        mBtnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(
                        new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                                e.onNext(1);
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                printMsgToTextView1(String.valueOf(integer));
                            }
                        });
            }
        });

        ObservableOnSubscribe<Integer> subscribe = new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> e) throws Exception {
                mBtnStart2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        e.onNext(1);
                    }
                });
            }
        };
        Observable.create(subscribe)
                .subscribeOn(Schedulers.computation())
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        printMsgToTextView2("多次点击");
                    }
                });
    }



    private void printMsgToTextView1(String msg) {
        mTvContent1.append(msg);
        mTvContent1.append("\n");
    }

    private void printMsgToTextView2(String msg) {
        mTvContent2.append(msg);
        mTvContent2.append("\n");
    }


}
