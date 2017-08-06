package com.kezhong.app.gankio.network;

import com.kezhong.app.gankio.entity.NormalData;
import com.kezhong.app.gankio.model.NormalResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/6
 */

public interface GankApi {

	@GET("data/{cate}/{count}/{page}")
	Observable<NormalResult> getNormalData(@Path("cate") String category, @Path("count") int count, @Path("page") int page);

}
