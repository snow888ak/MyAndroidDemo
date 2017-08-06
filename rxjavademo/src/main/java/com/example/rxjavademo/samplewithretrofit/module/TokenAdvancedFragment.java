package com.example.rxjavademo.samplewithretrofit.module;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjavademo.R;
import com.example.rxjavademo.samplewithretrofit.model.FakeThing;
import com.example.rxjavademo.samplewithretrofit.model.FakeToken;
import com.example.rxjavademo.samplewithretrofit.network.NetWork;
import com.example.rxjavademo.samplewithretrofit.network.api.FakeApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/2
 */

public class TokenAdvancedFragment extends Fragment {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mRefreshLayout;
	@BindView(R.id.tv_token_data)
	TextView mTvToken;

	private final FakeToken cachedFakeToken = new FakeToken(true);

	private boolean tokenUpdate = false;

	private Disposable mDisposable;

	public static TokenAdvancedFragment newInstance(){
		return new TokenAdvancedFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_token_advanced, container, false);
		ButterKnife.bind(this, view);

		mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.GREEN);
		mRefreshLayout.setEnabled(false);

		return view;
	}

	@OnClick(R.id.loadBtn)
	void loadData(){
		mRefreshLayout.setRefreshing(true);
		tokenUpdate = false;
		FakeApi api = NetWork.getTokenApi();
		mDisposable = method1(api);
	}

	private Disposable method1(final FakeApi api){
		return Observable.just(cachedFakeToken)
				.flatMap(new Function<FakeToken, ObservableSource<FakeThing>>() {
					@Override
					public ObservableSource<FakeThing> apply(@NonNull FakeToken fakeToken) throws Exception {
						return fakeToken.mToken == null ?
								Observable.<FakeThing>error(new NullPointerException("Token is null!")) :
								api.getFakeData(fakeToken);
					}
				})
				.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
					@Override
					public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
						return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
							@Override
							public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
								if (throwable instanceof IllegalArgumentException || throwable instanceof NullPointerException) {
									return api.getFakeToken("fake_auth_code")
											.doOnNext(new Consumer<FakeToken>() {
												@Override
												public void accept(@NonNull FakeToken fakeToken) throws Exception {
													tokenUpdate = true;
													cachedFakeToken.mToken = fakeToken.mToken;
													cachedFakeToken.expired = fakeToken.expired;
												}
											});
								}
								return Observable.error(throwable);
							}
						});
					}
				})
				.observeOn(Schedulers.io())
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<FakeThing>() {
					@Override
					public void accept(@NonNull FakeThing fakeThing) throws Exception {
						mRefreshLayout.setRefreshing(false);
						String token = cachedFakeToken.mToken;
						if (tokenUpdate) {
							token += "("+getString(R.string.updated)+")";
						}
						mTvToken.setText(getString(R.string.got_token_and_data, token, fakeThing.id, fakeThing.name));
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@NonNull Throwable throwable) throws Exception {
						mRefreshLayout.setRefreshing(false);
						Toast.makeText(getActivity(), getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void unSubscrible(){
		if(mDisposable != null && mDisposable.isDisposed()) {
			mDisposable.dispose();
		}
	}


	@OnClick(R.id.invalidateBtn)
	void invalidateToken(){
		cachedFakeToken.expired = true;
		Toast.makeText(getActivity(), R.string.token_destroyed, Toast.LENGTH_SHORT).show();
	}
}
