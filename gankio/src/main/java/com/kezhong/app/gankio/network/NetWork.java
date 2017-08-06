package com.kezhong.app.gankio.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/6
 */

public class NetWork {

	public static GankApi mGankApi;

	public static GankApi getGankApi(){
		if (mGankApi == null) {
			synchronized (NetWork.class) {
				if (mGankApi == null) {
					Retrofit retrofit = new Retrofit.Builder()
							.client(new OkHttpClient())
							.baseUrl("http://gank.io/api/")
							.addConverterFactory(GsonConverterFactory.create())
							.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
							.build();
					mGankApi = retrofit.create(GankApi.class);
				}
			}
		}
		return mGankApi;
	}

}
