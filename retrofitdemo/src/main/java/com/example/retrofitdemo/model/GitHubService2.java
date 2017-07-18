package com.example.retrofitdemo.model;

import com.example.retrofitdemo.entity.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/17
 */

public interface GitHubService2 {

	@GET("users/{user}/repos")
	Observable<List<Repo>> listRepos(@Path("user") String user);

}
