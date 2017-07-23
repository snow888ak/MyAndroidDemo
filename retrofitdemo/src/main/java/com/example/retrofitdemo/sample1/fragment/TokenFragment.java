package com.example.retrofitdemo.sample1.fragment;

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

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.model.FakeThing;
import com.example.retrofitdemo.sample1.model.FakeToken;
import com.example.retrofitdemo.sample1.network.NetWork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class TokenFragment extends Fragment {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeLayout;
	@BindView(R.id.tv_token_data)
	TextView mTvToken;
	
	public static TokenFragment newInstance(){
		return new TokenFragment();
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_token, container, false);
		ButterKnife.bind(this, view);

		mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.GREEN);
		mSwipeLayout.setEnabled(false);
		return view;
	}
	
	@OnClick(R.id.loadBtn)
	void loadTokenData(){
		mSwipeLayout.setRefreshing(true);
		NetWork.getTokenApi()
				.getFakeToken("fake_auth_code")
				.flatMap(new Function<FakeToken, ObservableSource<FakeThing>>() {
					@Override
					public ObservableSource<FakeThing> apply(@NonNull FakeToken fakeToken) throws Exception {
						return NetWork.getTokenApi().getFakeData(fakeToken);
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<FakeThing>() {
					@Override
					public void accept(@NonNull FakeThing fakeThing) throws Exception {
						mSwipeLayout.setRefreshing(false);
						mTvToken.setText(getString(R.string.got_data, fakeThing.id, fakeThing.name));
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@NonNull Throwable throwable) throws Exception {
						mSwipeLayout.setRefreshing(false);
						Toast.makeText(getActivity(), getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
					}
				});
				
	}
	
}
