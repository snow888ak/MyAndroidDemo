package com.example.retrofitdemo.sample1.network.api;

import com.example.retrofitdemo.sample1.model.GankBeautyResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public interface GankApi {

	@GET("data/福利/{count}/{page}")
	Observable<GankBeautyResult> getBeauties(@Path("count") int count, @Path("page") int page);

}
