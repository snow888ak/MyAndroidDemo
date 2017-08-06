package com.example.rxjavademo.samplewithretrofit.module.cache_6;

import com.example.rxjavademo.context.App;
import com.example.rxjavademo.samplewithretrofit.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/5
 */

public class DataBase {

	private static DataBase mInstance;

	private final String DATA_FILE_NAME = "data.db";
	private Gson gson = new Gson();
	private File dataFile = new File(App.getInstance().getFilesDir(), DATA_FILE_NAME);

	private DataBase() {

	}

	public static DataBase getInstance() {
		if (mInstance == null) {
			synchronized (DataBase.class) {
				if (mInstance == null) {
					mInstance = new DataBase();
				}
			}
		}
		return mInstance;
	}

	public void writeData(List<Item> datas) {
		String json = gson.toJson(datas);
		try{
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
			Writer writer = new FileWriter(dataFile);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Item> readData(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try{
			Reader reader = new FileReader(dataFile);
			return gson.fromJson(reader, new TypeToken<List<Item>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(){
		dataFile.delete();
	}

}
