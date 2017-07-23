package com.example.retrofitdemo.sample1.network;

import com.example.retrofitdemo.sample1.network.api.GankApi;
import com.example.retrofitdemo.sample1.network.api.FakeApi;
import com.example.retrofitdemo.sample1.network.api.ZhuangbiApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/21
 */

public class NetWork {

	private static ZhuangbiApi mZhuangbiApi;
	private static GankApi mGankApi;
	private static FakeApi mFakeApi;

	public static ZhuangbiApi getZhuangbiApi(){
		if (mZhuangbiApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(Constans.URL_ZHUANG_BI)
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			mZhuangbiApi = retrofit.create(ZhuangbiApi.class);
		}
		return mZhuangbiApi;
	}

	public static GankApi getGankApi(){
		if (mGankApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(Constans.URL_GANK_API)
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			mGankApi = retrofit.create(GankApi.class);
		}
		return mGankApi;
	}

	public static FakeApi getTokenApi(){
		if (mFakeApi == null) {
			mFakeApi = new FakeApi();
		}
		return mFakeApi;
	}

}
