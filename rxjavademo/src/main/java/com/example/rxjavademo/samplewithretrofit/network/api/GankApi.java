package com.example.rxjavademo.samplewithretrofit.network.api;

import com.example.rxjavademo.samplewithretrofit.model.GankBeautyResult;

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
