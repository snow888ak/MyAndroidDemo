package com.example.rxjavademo.samplewithretrofit.module.cache_6;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjavademo.R;
import com.example.rxjavademo.samplewithretrofit.adapter.ItemListAdapter;
import com.example.rxjavademo.samplewithretrofit.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/5
 */

public class CacheFragment extends Fragment {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeLayout;
	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.tv_label)
	TextView mTvLabel;

	ItemListAdapter mAdapter = new ItemListAdapter();

	long startTime;

	public static CacheFragment newInstance(){
		return new CacheFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cache_6, container, false);
		ButterKnife.bind(this, view);

		mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.GREEN);
		mSwipeLayout.setEnabled(false);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		mRecyclerView.setAdapter(mAdapter);
		return view;
	}

	@OnClick(R.id.loadData)
	public void loadData(){
		mSwipeLayout.setRefreshing(true);
		startTime = System.currentTimeMillis();
		Data.getInstance().subscribeData(new Observer<List<Item>>() {
			@Override
			public void onSubscribe(@NonNull Disposable d) {

			}

			@Override
			public void onNext(@NonNull List<Item> items) {
				mSwipeLayout.setRefreshing(false);
				long loadTime = System.currentTimeMillis() - startTime;
				mTvLabel.setText(getString(R.string.loading_time_and_source, String.valueOf(loadTime), Data.getInstance().getDataSourceText()));
				mAdapter.setDatas(items);
			}

			@Override
			public void onError(@NonNull Throwable e) {
				e.printStackTrace();
				mSwipeLayout.setRefreshing(false);
				Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete() {

			}
		});
	}

	@OnClick(R.id.btn_clear_memory_cache)
	void clearMemoryCache(){
		Data.getInstance().clearMemory();
	}

	@OnClick(R.id.btn_clear_all_cache)
	void clearMemoryAndDiskCache(){
		Data.getInstance().clearMemoryAndDiskCache();
	}

}
