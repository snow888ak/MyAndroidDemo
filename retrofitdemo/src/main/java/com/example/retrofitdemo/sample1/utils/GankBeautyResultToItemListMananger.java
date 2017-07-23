package com.example.retrofitdemo.sample1.utils;

import com.example.retrofitdemo.sample1.model.GankBeauty;
import com.example.retrofitdemo.sample1.model.GankBeautyResult;
import com.example.retrofitdemo.sample1.model.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class GankBeautyResultToItemListMananger implements Function<GankBeautyResult, List<Item>> {

	private static GankBeautyResultToItemListMananger mInstance;

	private GankBeautyResultToItemListMananger() {

	}

	public static GankBeautyResultToItemListMananger getInstance() {
		if (mInstance == null) {
			synchronized (GankBeautyResultToItemListMananger.class) {
				if (mInstance == null) {
					mInstance = new GankBeautyResultToItemListMananger();
				}
			}
		}
		return mInstance;
	}

	@Override
	public List<Item> apply(@NonNull GankBeautyResult gankBeautyResult) throws Exception {
		List<GankBeauty> beauties = gankBeautyResult.getBeauties();
		List<Item> result = new ArrayList<>();
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
		SimpleDateFormat outDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		for(GankBeauty beauty : beauties) {
			Item item = new Item();
			try{
				Date publishDate = inputDateFormat.parse(beauty.getPublishedAt());
				item.description = outDateFormat.format(publishDate);
			} catch (ParseException e) {
				item.description = "unknow date";
			}
			item.image_url = beauty.getUrl();
			result.add(item);
		}
		return result;
	}
}
