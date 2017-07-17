package com.example.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.retrofitdemo.entity.Repo;
import com.example.retrofitdemo.model.GitHubService;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BuildConfig.BaseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		GitHubService service = retrofit.create(GitHubService.class);
		final Call<List<Repo>> call = service.listRepos("snow888ak");
		findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				call.enqueue(new Callback<List<Repo>>() {
					@Override
					public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
						Log.d("Retrofit", "onResponse: response" + new Gson().toJson(response));
					}

					@Override
					public void onFailure(Call<List<Repo>> call, Throwable t) {
						Log.d("Retrofit", "onFailure: Throwable" + new Gson().toJson(t));
					}
				});
			}
		});
	}
}
