package com.example.rxjavademo.samplewithretrofit.model;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class FakeToken {

	public String mToken;
	public boolean expired;

	public FakeToken() {
	}

	public FakeToken(boolean expired) {
		this.expired = expired;
	}
}
