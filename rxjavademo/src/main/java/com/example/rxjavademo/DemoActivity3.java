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
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity3 extends AppCompatActivity {

    public static final String TAG = DemoActivity3.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        setTitle("不完整定义回调");
    }

    public void startClick(View v){
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                mTvContent.append("onNextAction " + s);
                mTvContent.append("\n");
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                mTvContent.append("onErrorAction " + e.getMessage());
            }
        };
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                mTvContent.append("onCompleteAction ");
                mTvContent.append("\n");
            }
        };
        String[] fromArrays = {"from_1", "from_2", "from_3"};
        //自动创建Subscriber,并使用onNextAction来定义onNext();
        Observable.from(fromArrays).subscribe(onNextAction);
        //自动创建Subscriber,并使用onNextAction和onErrorAction来定义onNext()和onError();
        Observable.from(fromArrays).subscribe(onNextAction, onErrorAction);
        //自动创建Subscriber,并使用onNextAction、onErrorAction和onCompleteAction来定义onNext()、onError()和onComplete();
        Observable.from(fromArrays).subscribe(onNextAction, onErrorAction, onCompleteAction);
    }

}
