package com.kezhong.app.gankio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/6
 */

public abstract class BaseFragment extends Fragment {

	private Unbinder unbinder;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		unbinder = ButterKnife.bind(this, view);
		initView();
		initData();
		return view;
	}

	protected abstract int getLayoutId();

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	protected abstract void initView();

	protected abstract void initData();
}
