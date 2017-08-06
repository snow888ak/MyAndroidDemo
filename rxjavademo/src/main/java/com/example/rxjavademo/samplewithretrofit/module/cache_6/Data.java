package com.example.rxjavademo.samplewithretrofit.module.cache_6;

import android.support.annotation.IntDef;

import com.example.rxjavademo.R;
import com.example.rxjavademo.context.App;
import com.example.rxjavademo.samplewithretrofit.model.GankBeautyResult;
import com.example.rxjavademo.samplewithretrofit.model.Item;
import com.example.rxjavademo.samplewithretrofit.network.NetWork;
import com.example.rxjavademo.samplewithretrofit.utils.GankBeautyResultToItemListMananger;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/5
 */

public class Data {
	private static Data mInstance;
	public static final int DATA_SOURCE_MEMORY = 1;
	public static final int DATA_SOURCE_DISK = 2;
	public static final int DATA_SOURCE_NETWORK = 3;

	@IntDef({DATA_SOURCE_MEMORY, DATA_SOURCE_DISK, DATA_SOURCE_NETWORK})
	@interface DataSource {
	}

	;

	BehaviorSubject<List<Item>> cache;//缓存

	private
	@DataSource
	int dataSource;

	private Data() {

	}

	public static Data getInstance() {
		if (mInstance == null) {
			synchronized (Data.class) {
				if (mInstance == null) {
					mInstance = new Data();
				}
			}
		}
		return mInstance;
	}

	private void setDataSource(@DataSource int dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获取数据源文字描述
	 *
	 * @return
	 */
	public String getDataSourceText() {
		int dataSourceTextRes = 0;
		switch (dataSource) {
			case DATA_SOURCE_MEMORY:
				dataSourceTextRes = R.string.data_source_memory;
				break;
			case DATA_SOURCE_DISK:
				dataSourceTextRes = R.string.data_source_disk;
				break;
			case DATA_SOURCE_NETWORK:
				dataSourceTextRes = R.string.data_source_network;
				break;
			default:
				return "";
		}
		return App.getInstance().getString(dataSourceTextRes);
	}

	/**
	 * 从网络获取数据
	 */
	public void loadFromNetWork() {
		NetWork.getGankApi()
				.getBeauties(100, 1)
				.subscribeOn(Schedulers.io())
				.map(GankBeautyResultToItemListMananger.getInstance())
				.doOnNext(new Consumer<List<Item>>() {
					@Override
					public void accept(@NonNull List<Item> items) throws Exception {
						DataBase.getInstance().writeData(items);
					}
				})
				.subscribe(new Consumer<List<Item>>() {
					@Override
					public void accept(@NonNull List<Item> items) throws Exception {
						cache.onNext(items);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@NonNull Throwable throwable) throws Exception {
						throwable.printStackTrace();
					}
				});
	}

	public void subscribeData(@NonNull Observer<List<Item>> observer) {
		if (cache == null) {
			cache = BehaviorSubject.create();
			Observable.create(new ObservableOnSubscribe<List<Item>>() {
				@Override
				public void subscribe(@NonNull ObservableEmitter<List<Item>> e) throws Exception {
					List<Item> items = DataBase.getInstance().readData();
					if (items == null) {
						setDataSource(DATA_SOURCE_NETWORK);
						loadFromNetWork();
					} else {
						setDataSource(DATA_SOURCE_DISK);
						e.onNext(items);
					}
				}
			})
					.subscribeOn(Schedulers.io())
					.subscribe(cache);
		} else {
			setDataSource(DATA_SOURCE_MEMORY);
			cache.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
		}
	}

	/**
	 * 清除内存缓存
	 */
	public void clearMemory() {
		cache = null;
	}

	/**
	 * 清除内存与磁盘缓存
	 */
	public void clearMemoryAndDiskCache() {
		clearMemory();
		DataBase.getInstance().delete();
	}

}
