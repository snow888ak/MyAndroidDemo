package com.example.rxjavademo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rxjavademo.model.IStudentModel;
import com.example.rxjavademo.model.StudentModelImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity7 extends AppCompatActivity {

    public static final String TAG = DemoActivity7.class.getSimpleName();

    @BindView(R.id.content)
    TextView mTvContent;

    private IStudentModel studentModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        setTitle("操作符");
        studentModel = new StudentModelImpl();
    }

    public void startClick(View v) {
        Observable.just("http://www.open-open.com/")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String s) {
                        try {
                            return Jsoup.connect(s).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .filter(new Func1<Document, Boolean>() {
                    @Override
                    public Boolean call(Document document) {
                        return document != null;
                    }
                })
                .flatMap(new Func1<Document, Observable<Element>>() {
                    @Override
                    public Observable<Element> call(Document document) {
                        Elements elements = document.getElementsByTag("a");
                        return Observable.from(elements.toArray(new Element[elements.size()]));
                    }
                })
                .filter(new Func1<Element, Boolean>() {
                    @Override
                    public Boolean call(Element element) {
                        return element != null;
                    }
                })
                .map(new Func1<Element, String>() {
                    @Override
                    public String call(Element element) {
                        return element.attr("href");
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.startsWith("http:") && s.endsWith(".html");
                    }
                })
                .distinct()//去重
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            String title = Jsoup.connect(s).get().title();
                            return s + "\n" + title;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
