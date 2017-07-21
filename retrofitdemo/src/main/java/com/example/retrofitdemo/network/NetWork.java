package com.example.retrofitdemo.network;

import com.example.retrofitdemo.network.api.ZhuangbiApi;

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

}
