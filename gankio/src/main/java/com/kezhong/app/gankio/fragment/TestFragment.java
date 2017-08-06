package com.kezhong.app.gankio.fragment;

import android.widget.TextView;

import com.example.lib.utils.JsonUtil;
import com.kezhong.app.gankio.R;
import com.kezhong.app.gankio.model.NormalResult;
import com.kezhong.app.gankio.network.NetWork;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/6
 */

public class TestFragment extends BaseFragment {

	private String mContent;

	@BindView(R.id.tv_content)
	TextView mTvContent;

	public static TestFragment newInstance(String content){
		TestFragment result = new TestFragment();
		result.mContent = content;
		return result;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_test;
	}

	@Override
	protected void initView() {
		mTvContent.setText(mContent);
	}

	@Override
	protected void initData() {
		NetWork.getGankApi().getNormalData(mContent, 10, 1)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<NormalResult>() {
					@Override
					public void accept(@NonNull NormalResult normalResult) throws Exception {
						mTvContent.setText(JsonUtil.toJson(normalResult));
					}
				});
	}

}
