package com.kezhong.app.gankio;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kezhong.app.gankio.fragment.BaseFragment;
import com.kezhong.app.gankio.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.tabLayout)
	TabLayout mTabLayout;
	@BindView(R.id.viewpager)
	ViewPager mPager;

	private PageAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		setSupportActionBar(mToolbar);
		mAdapter = new PageAdapter(getSupportFragmentManager());
		mAdapter.setTitles(getResources().getStringArray(R.array.category));
		mAdapter.setFragments(createPageFragments());
		mPager.setAdapter(mAdapter);
		mTabLayout.setupWithViewPager(mPager);
	}

	private List<BaseFragment> createPageFragments(){
		List<BaseFragment> result = new ArrayList<>();
		String[] title = getResources().getStringArray(R.array.category);
		for (int i = 0, count = title.length; i < count; i++) {
			result.add(TestFragment.newInstance(title[i]));
		}
		return result;
	}

	private class PageAdapter extends FragmentStatePagerAdapter{

		private List<BaseFragment> mFragments = new ArrayList<>();

		private String[] mTitles = new String[0];

		public PageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			try {
				return mTitles[position];
			} catch (IndexOutOfBoundsException e) {
				return super.getPageTitle(position);
			}
		}

		/**
		 * 设置子界面
		 * @param fragments
		 */
		public void setFragments(List<BaseFragment> fragments) {
			mFragments.clear();
			if (fragments != null && !fragments.isEmpty()) {
				mFragments.addAll(fragments);
			}
			notifyDataSetChanged();;
		}

		/**
		 * 设置标题
		 * @param titles
		 */
		public void setTitles(String[] titles) {
			if (titles != null) {
				mTitles = titles;
			}
			notifyDataSetChanged();
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}
	}
}
