package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Snow.ZhK on 2017/7/9.
 */

public class DemoActivity8 extends AppCompatActivity {

	public static final String TAG = DemoActivity8.class.getSimpleName();

	@BindView(R.id.content)
	TextView mTvContent;

	@BindView(R.id.btn_start)
	Button mBtnStart;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		ButterKnife.bind(this);
		mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		setTitle("监听按钮点击次数");
		Observable<Boolean> clickStream = Observable.create(new SomeOnSubscribe(mBtnStart));
		clickStream.buffer(1, TimeUnit.SECONDS)
				.map(new Function<List<Boolean>, Integer>() {
					@Override
					public Integer apply(@NonNull List<Boolean> booleen) throws Exception {
						return booleen.size();
					}
				})
				.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(@NonNull Integer integer) throws Exception {
						printMsgToTextView(String.valueOf(integer));
					}
				});
	}

	private void printMsgToTextView(String msg) {
		mTvContent.append(msg);
		mTvContent.append("\n");
	}

	class SomeOnSubscribe implements ObservableOnSubscribe<Boolean> {

		private View mView;

		public SomeOnSubscribe(View mView) {
			this.mView = mView;
		}

		@Override
		public void subscribe(@NonNull final ObservableEmitter<Boolean> e) throws Exception {
//			mView.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					e.onNext(true);
//				}
//			});
			e.onNext(true);
		}
	}

}
