package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.rxjavademo.model.IStudentModel;
import com.example.rxjavademo.model.StudentModelImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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
                .map(new Function<String, Document>() {
                    @Override
                    public Document apply(@NonNull String s) throws Exception {
                        try {
                            return Jsoup.connect(s).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .filter(new Predicate<Document>() {
                    @Override
                    public boolean test(@NonNull Document document) throws Exception {
                        return document != null;
                    }
                })
                .flatMap(new Function<Document, ObservableSource<Element>>() {
                    @Override
                    public ObservableSource<Element> apply(@NonNull Document document) throws Exception {
                        return Observable.fromIterable(document.getElementsByTag("a"));
                    }
                })
                .filter(new Predicate<Element>() {
                    @Override
                    public boolean test(@NonNull Element element) throws Exception {
                        return element != null;
                    }
                })
                .map(new Function<Element, String>() {
                    @Override
                    public String apply(@NonNull Element element) throws Exception {
                        return element.attr("href");
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s.startsWith("http:") && s.endsWith(".html");
                    }
                })
                .distinct()//去重
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        try {
                            String title = Jsoup.connect(s).get().title();
                            return s + "\n" + title;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
