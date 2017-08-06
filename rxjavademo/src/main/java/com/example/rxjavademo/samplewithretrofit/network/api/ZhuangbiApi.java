package com.example.rxjavademo.samplewithretrofit.network.api;

import com.example.rxjavademo.samplewithretrofit.model.ZhuangbiImage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/21
 */

public interface ZhuangbiApi {

	@GET("search")
	Observable<List<ZhuangbiImage>> search(@Query("q") String query);

}
