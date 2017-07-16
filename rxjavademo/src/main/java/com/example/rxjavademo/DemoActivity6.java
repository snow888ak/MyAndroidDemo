package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.rxjavademo.model.IStudentModel;
import com.example.rxjavademo.model.StudentModelImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity6 extends AppCompatActivity {

    public static final String TAG = DemoActivity6.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    private IStudentModel studentModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        setTitle("多次变换");
        studentModel = new StudentModelImpl();
    }

    public void startClick(View v) {
        Function<Integer, String> operator1 = new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        };
        Function<String, Double> operator2 = new Function<String, Double>() {
            @Override
            public Double apply(@NonNull String s) throws Exception {
                Double result = new Double(-1);
                try{
                    result = Double.valueOf(s);
                } catch (NumberFormatException e) {

                }
                return result;
            }
        };
        Function<Double, String> operator3 = new Function<Double, String>() {
            @Override
            public String apply(@NonNull Double aDouble) throws Exception {
                return String.valueOf(aDouble);
            }
        };
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(operator1)
                .map(operator2)
                .map(operator3)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        printMsgToTextView(s);
                    }
                });
    }

    private void printMsgToTextView(String msg) {
        mTvContent.append(msg);
        mTvContent.append("\n");
    }
}
