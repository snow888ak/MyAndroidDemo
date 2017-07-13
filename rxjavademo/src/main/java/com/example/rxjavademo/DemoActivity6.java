package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.lib.utils.JsonUtil;
import com.example.rxjavademo.entity.Course;
import com.example.rxjavademo.entity.Student;
import com.example.rxjavademo.model.IStudentModel;
import com.example.rxjavademo.model.StudentModelImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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
        Func1<Integer, String> operator1 = new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return String.valueOf(integer);
            }
        };
        Func1<String, Double> operator2 = new Func1<String, Double>() {
            @Override
            public Double call(String s) {
                Double result = new Double(-1);
                try{
                    result = Double.valueOf(s);
                } catch (NumberFormatException e) {

                }
                return result;
            }
        };
        Func1<Double, String> operator3 = new Func1<Double, String>() {
            @Override
            public String call(Double aDouble) {
                return String.valueOf(aDouble);
            }
        };
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(operator1)
                .map(operator2)
                .map(operator3)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printMsgToTextView(s);
                    }
                });
    }

    private void printMsgToTextView(String msg) {
        mTvContent.append(msg);
        mTvContent.append("\n");
    }
}
