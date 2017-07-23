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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.adapter.ItemListAdapter;
import com.example.retrofitdemo.sample1.model.GankBeauty;
import com.example.retrofitdemo.sample1.model.GankBeautyResult;
import com.example.retrofitdemo.sample1.model.Item;
import com.example.retrofitdemo.sample1.network.NetWork;
import com.example.retrofitdemo.sample1.utils.GankBeautyToItemManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class MapFragment extends Fragment {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefresh;
	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.previousPageBtn)
	Button mPreviousBtn;
	@BindView(R.id.nextPageBtn)
	Button mNextBtn;
	@BindView(R.id.tv_count)
	TextView mTvCount;
	ItemListAdapter mAdapter = new ItemListAdapter();
	Observer<List<Item>> observer = new Observer<List<Item>>() {
		@Override
		public void onSubscribe(@NonNull Disposable d) {

		}

		@Override
		public void onNext(@NonNull List<Item> items) {
			mSwipeRefresh.setRefreshing(false);
			mAdapter.setDatas(items);
		}

		@Override
		public void onError(@NonNull Throwable e) {
			mSwipeRefresh.setRefreshing(false);
			Toast.makeText(getActivity(), "数据加载出错！", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete() {
			mTvCount.setText(String.format("第%d页", mPage));
		}
	};

	private int mPage = 0; //当前页数
	private int mCount = 10;//

	public static MapFragment newInstance(){
		return new MapFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		ButterKnife.bind(this, view);

		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		mRecyclerView.setAdapter(mAdapter);

		mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
		mSwipeRefresh.setEnabled(false);
		return view;
	}

	@OnClick(R.id.previousPageBtn)
	void previousPage(){
		loadPage(--mPage);
		if (mPage == 1) {
			mPreviousBtn.setEnabled(false);
		}
	}

	@OnClick(R.id.nextPageBtn)
	void nextPage(){
		loadPage(++mPage);
		if (mPage >= 2) {
			mPreviousBtn.setEnabled(true);
		}
	}

	private void loadPage(int page){
		NetWork.getGankApi()
				.getBeauties(mCount, page)
				.flatMap(new Function<GankBeautyResult, ObservableSource<GankBeauty>>() {
					@Override
					public ObservableSource<GankBeauty> apply(@NonNull GankBeautyResult gankBeautyResult) throws Exception {
						return Observable.fromIterable(gankBeautyResult.getBeauties());
					}
				})
				.map(GankBeautyToItemManager.getInstance())
				.buffer(mCount)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);
	}

}
