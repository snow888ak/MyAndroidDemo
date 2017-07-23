package com.example.retrofitdemo.sample1.utils;

import com.example.retrofitdemo.sample1.model.GankBeauty;
import com.example.retrofitdemo.sample1.model.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/7/23
 */

public class GankBeautyToItemManager implements Function<GankBeauty, Item> {

	private static GankBeautyToItemManager mInstance;

	private GankBeautyToItemManager() {

	}

	public static GankBeautyToItemManager getInstance() {
		if (mInstance == null) {
			synchronized (GankBeautyToItemManager.class) {
				if (mInstance == null) {
					mInstance = new GankBeautyToItemManager();
				}
			}
		}
		return mInstance;
	}

	@Override
	public Item apply(@NonNull GankBeauty gankBeauty) throws Exception {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
		SimpleDateFormat outDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Item item = new Item();
		try{
			Date publishDate = inputDateFormat.parse(gankBeauty.getPublishedAt());
			item.description = outDateFormat.format(publishDate);
		} catch (ParseException e) {
			item.description = "unknow date";
		}
		item.image_url = gankBeauty.getUrl();
		return item;
	}


}
