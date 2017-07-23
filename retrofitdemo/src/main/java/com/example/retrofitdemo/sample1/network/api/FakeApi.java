package com.example.retrofitdemo.sample1.network.api;


import android.support.annotation.NonNull;

import com.example.retrofitdemo.sample1.model.FakeThing;
import com.example.retrofitdemo.sample1.model.FakeToken;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class FakeApi {
	Random random = new Random();

	public Observable<FakeToken> getFakeToken(@NonNull String fakeAuth) {
		return Observable.just(fakeAuth)
				.map(new Function<String, FakeToken>() {
					@Override
					public FakeToken apply(@io.reactivex.annotations.NonNull String s) throws Exception {
						// Add some random delay to mock the network delay
						int fakeNetworkTimeCost = random.nextInt(500) + 500;
						try {
							Thread.sleep(fakeNetworkTimeCost);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						FakeToken token = new FakeToken();
						token.mToken = createToken();
						return token;
					}
				});
	}

	private static String createToken(){
		return "fake_token_" + System.currentTimeMillis() % 10000;
	}

	public Observable<FakeThing> getFakeData(final FakeToken token) {
		return Observable.just(token)
				.map(new Function<FakeToken, FakeThing>() {
					@Override
					public FakeThing apply(@io.reactivex.annotations.NonNull FakeToken fakeToken) throws Exception {
						int fakeNetworkTimeCost = random.nextInt(500) + 500;
						try{
							Thread.sleep(fakeNetworkTimeCost);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (token.expired) {
							throw new IllegalArgumentException("Token expired!");
						}
						FakeThing thing = new FakeThing();
						thing.id = (int) (System.currentTimeMillis() % 1000);
						thing.name = "FAKE_USER_" + thing.id;
						return thing;
					}
				});
	}


}
