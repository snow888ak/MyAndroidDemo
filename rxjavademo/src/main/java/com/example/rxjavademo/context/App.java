package com.example.rxjavademo.context;

import android.app.Application;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/5
 */

public class App extends Application {

	private static App mInstance;

	public App(){
		super();
		mInstance = this;
	}

	public static App getInstance(){
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
