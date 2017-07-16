package com.example.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity4 extends AppCompatActivity {

	public static final String TAG = DemoActivity4.class.getSimpleName();

	@BindView(R.id.img)
	ImageView mImg;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_img);
		ButterKnife.bind(this);
		setTitle("通过id加载图片");
	}

	public void startClick(View v) {
		final int id = R.mipmap.ic_launcher;
		Observable.create(new ObservableOnSubscribe<Drawable>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Drawable> e) throws Exception {
				Drawable drawable = getResources().getDrawable(id);
				e.onNext(drawable);
			}
		})
				.subscribeOn(Schedulers.io())//指定subscribe()发生在IO线程，本例中指定在io线程获取图片
				.observeOn(AndroidSchedulers.mainThread())//指定Subscriber的回调发生在主线程
				.subscribe(new Observer<Drawable>() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {

					}

					@Override
					public void onNext(@NonNull Drawable drawable) {
						mImg.setImageDrawable(drawable);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						Toast.makeText(DemoActivity4.this, "图片加载出错！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete() {

					}
				});
	}

}
