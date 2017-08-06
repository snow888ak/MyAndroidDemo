package com.example.rxjavademo.samplewithretrofit.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rxjavademo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/20
 */

public class TestPageFragment extends Fragment {

	private static final String TAG_CONTENT = "tagContent";

	private String mContent;

	@BindView(R.id.tv_content)
	TextView mTvContent;

	public static TestPageFragment newInstance(String content){
		TestPageFragment fragment = new TestPageFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TAG_CONTENT, content);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String content = getArguments().getString(TAG_CONTENT);
		this.mContent = content;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_test_pager, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTvContent.setText(mContent);
	}
}
