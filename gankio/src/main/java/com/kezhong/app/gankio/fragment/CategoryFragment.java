package com.kezhong.app.gankio.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lib.utils.JsonUtil;
import com.kezhong.app.gankio.R;
import com.kezhong.app.gankio.adapter.CategoryAdapter;
import com.kezhong.app.gankio.entity.NormalData;
import com.kezhong.app.gankio.model.NormalResult;
import com.kezhong.app.gankio.network.NetWork;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Snow on 2017/8/7.
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CategoryAdapter mAdapter = new CategoryAdapter();
    private String mContent;

    public static CategoryFragment newInstance(String content){
        CategoryFragment result = new CategoryFragment();
        result.mContent = content;
        return result;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        NetWork.getGankApi().getNormalData(mContent, 10, 1)
                .map(new Function<NormalResult, List<NormalData>>() {
                    @Override
                    public List<NormalData> apply(@NonNull NormalResult normalResult) throws Exception {
                        return normalResult.results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NormalData>>() {
                    @Override
                    public void accept(@NonNull List<NormalData> normalDatas) throws Exception {
                        mAdapter.setDatas(normalDatas);
                    }
                });
    }
}
