package com.example.retrofitdemo.sample1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.fragment.TestPageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/20
 */

public class SampleMainActivity extends AppCompatActivity {

	@BindView(R.id.tool_bar)
	Toolbar mToolBar;
	@BindView(android.R.id.tabs)
	TabLayout mTabs;
	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample1_main);
		ButterKnife.bind(this);
		setSupportActionBar(mToolBar);
		initView();
	}

	private void initView(){
		final Fragment[] fragments = new Fragment[6];
		fragments[0] = TestPageFragment.newInstance("界面1");
		fragments[1] = TestPageFragment.newInstance("界面2");
		fragments[2] = TestPageFragment.newInstance("界面3");
		fragments[3] = TestPageFragment.newInstance("界面4");
		fragments[4] = TestPageFragment.newInstance("界面5");
		fragments[5] = TestPageFragment.newInstance("界面6");
		final String[] titles = {"TAB1", "标题2", "标题3", "标题4", "标题5", "标题6"};
		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return fragments[position];
			}

			@Override
			public int getCount() {
				return fragments.length;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titles[position];
			}
		});
		mTabs.setupWithViewPager(mViewPager);
		mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
}
