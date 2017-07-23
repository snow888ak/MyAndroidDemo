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
import android.widget.Toast;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.adapter.ItemListAdapter;
import com.example.retrofitdemo.sample1.model.GankBeauty;
import com.example.retrofitdemo.sample1.model.GankBeautyResult;
import com.example.retrofitdemo.sample1.model.Item;
import com.example.retrofitdemo.sample1.model.ZhuangbiImage;
import com.example.retrofitdemo.sample1.network.NetWork;
import com.example.retrofitdemo.sample1.utils.GankBeautyResultToItemListMananger;
import com.example.retrofitdemo.sample1.utils.GankBeautyToItemManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class ZipFragment extends Fragment {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefresh;
	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	ItemListAdapter mAdapter = new ItemListAdapter();

	Consumer<List<Item>> consumer = new Consumer<List<Item>>() {
		@Override
		public void accept(@NonNull List<Item> items) throws Exception {
			mSwipeRefresh.setRefreshing(false);
			mAdapter.setDatas(items);
		}
	};

	public static ZipFragment newInstance() {
		return new ZipFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zip, container, false);
		ButterKnife.bind(this, view);

		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		mRecyclerView.setAdapter(mAdapter);

		mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
		mSwipeRefresh.setEnabled(false);
		return view;
	}

	@OnClick(R.id.loadBtn)
	void loadData() {
		mSwipeRefresh.setRefreshing(true);
		Observable.zip(
				NetWork.getGankApi()
						.getBeauties(200, 1)
						.map(GankBeautyResultToItemListMananger.getInstance()),
				NetWork.getZhuangbiApi().search("装逼"),
				new BiFunction<List<Item>, List<ZhuangbiImage>, List<Item>>() {
					@Override
					public List<Item> apply(@NonNull List<Item> items, @NonNull List<ZhuangbiImage> zhuangbiImages) throws Exception {
						List<Item> result = new ArrayList<Item>();
						for(int i = 0, count = Math.min(items.size() /2, zhuangbiImages.size()); i < count; i++) {
							result.add(items.get(i * 2));
							result.add(items.get(i * 2 + 1));
							Item zhuangbiItem = new Item();
							ZhuangbiImage zhuangbiImage = zhuangbiImages.get(i);
							zhuangbiItem.description = zhuangbiImage.description;
							zhuangbiItem.image_url = zhuangbiImage.image_url;
							result.add(zhuangbiItem);
						}
						return result;
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer);
	}

	private Observable<List<Item>> getGankData() {
		Single<List<Item>> itemList = NetWork.getGankApi()
				.getBeauties(200, 1)
				.flatMap(new Function<GankBeautyResult, ObservableSource<GankBeauty>>() {
					@Override
					public ObservableSource<GankBeauty> apply(@NonNull GankBeautyResult gankBeautyResult) throws Exception {
						return Observable.fromIterable(gankBeautyResult.getBeauties());
					}
				})
				.map(GankBeautyToItemManager.getInstance())
				.toList()
				.subscribeOn(Schedulers.io());
		return Observable.fromArray(itemList.blockingGet());
	}

	private Observable<List<Item>> getZhuangbiData() {
		Observable<List<Item>> observable = NetWork.getZhuangbiApi()
				.search("装逼")
				.map(new Function<List<ZhuangbiImage>, List<Item>>() {
					@Override
					public List<Item> apply(@NonNull List<ZhuangbiImage> zhuangbiImages) throws Exception {
						List<Item> result = new ArrayList<Item>();
						for (ZhuangbiImage item : zhuangbiImages) {
							Item resultItem = new Item();
							resultItem.description = item.description;
							resultItem.image_url = item.image_url;
							result.add(resultItem);
						}
						return result;
					}
				})
				.subscribeOn(Schedulers.io());
		return observable;
	}
}
