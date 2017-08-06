package com.example.rxjavademo.samplewithretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rxjavademo.R;
import com.example.rxjavademo.samplewithretrofit.module.ElementaryFragment;
import com.example.rxjavademo.samplewithretrofit.module.MapFragment;
import com.example.rxjavademo.samplewithretrofit.module.TestPageFragment;
import com.example.rxjavademo.samplewithretrofit.module.TokenAdvancedFragment;
import com.example.rxjavademo.samplewithretrofit.module.TokenFragment;
import com.example.rxjavademo.samplewithretrofit.module.ZipFragment;
import com.example.rxjavademo.samplewithretrofit.module.cache_6.CacheFragment;

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
		fragments[4] = TokenAdvancedFragment.newInstance();
		fragments[5] = CacheFragment.newInstance();
		final String[] titles = {"基本", "转换(map)", "压合(zip)", "Token(flatMap)", "Token_高级(retryWhen)", "缓存(BehaviorSubject)"};
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
