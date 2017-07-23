package com.example.retrofitdemo.sample1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.sample1.fragment.ElementaryFragment;
import com.example.retrofitdemo.sample1.fragment.MapFragment;
import com.example.retrofitdemo.sample1.fragment.TestPageFragment;
import com.example.retrofitdemo.sample1.fragment.TokenFragment;
import com.example.retrofitdemo.sample1.fragment.ZipFragment;

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
		fragments[0] = ElementaryFragment.newInstance();
		fragments[1] = MapFragment.newInstance();
		fragments[2] = ZipFragment.newInstance();
		fragments[3] = TokenFragment.newInstance();
		fragments[4] = TestPageFragment.newInstance("界面5");
		fragments[5] = TestPageFragment.newInstance("界面6");
		final String[] titles = {"基本", "转换(map)", "压合(zip)", "Token(flatMap)", "标题5", "标题6"};
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
