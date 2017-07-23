package com.example.retrofitdemo.sample1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.network.NetWork;
import com.example.retrofitdemo.sample1.adapter.ZhuangBiListAdapter;
import com.example.retrofitdemo.sample1.model.ZhuangbiImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/21
 */

public class ElementaryFragment extends Fragment {

	private ZhuangBiListAdapter mAdapter = new ZhuangBiListAdapter();

	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeLayout;

	Observer<List<ZhuangbiImage>> observer = new Observer<List<ZhuangbiImage>>() {
		@Override
		public void onSubscribe(@NonNull Disposable d) {

		}

		@Override
		public void onNext(@NonNull List<ZhuangbiImage> zhuangbiImages) {
			mSwipeLayout.setRefreshing(false);
			mAdapter.setDatas(zhuangbiImages);
		}

		@Override
		public void onError(@NonNull Throwable e) {
			mSwipeLayout.setRefreshing(false);
		}

		@Override
		public void onComplete() {
		}
	};

	public static ElementaryFragment newInstance(){
		return new ElementaryFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_elementary, container, false);
		ButterKnife.bind(this, view);

		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		mRecyclerView.setAdapter(mAdapter);
		mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
		mSwipeLayout.setEnabled(false);
		return view;
	}

	@OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
	void onTagChecked(RadioButton searchRb, boolean checked) {
		if (checked) {
			mAdapter.clear();
			mSwipeLayout.setRefreshing(true);
			search(searchRb.getText().toString());
		}
	}

	private void search(String key) {
		NetWork.getZhuangbiApi()
				.search(key)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);
	}
}
