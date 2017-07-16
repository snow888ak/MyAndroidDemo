package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
        Consumer<String> onNextAction = new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mTvContent.append("onNextAction " + s);
                mTvContent.append("\n");
            }
        };
        Consumer<Throwable> onErrorAction = new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable e) throws Exception {
                mTvContent.append("onErrorAction " + e.getMessage());
            }
        };
        Action onCompleteAction = new Action() {
            @Override
            public void run() throws Exception {
                mTvContent.append("onCompleteAction ");
                mTvContent.append("\n");
            }
        };
        String[] fromArrays = {"from_1", "from_2", "from_3"};
        //自动创建Subscriber,并使用onNextAction来定义onNext();
        Observable.fromArray(fromArrays).subscribe(onNextAction);
        //自动创建Subscriber,并使用onNextAction和onErrorAction来定义onNext()和onError();
        Observable.fromArray(fromArrays).subscribe(onNextAction, onErrorAction);
        //自动创建Subscriber,并使用onNextAction、onErrorAction和onCompleteAction来定义onNext()、onError()和onComplete();
        Observable.fromArray(fromArrays).subscribe(onNextAction, onErrorAction, onCompleteAction);
    }

}
