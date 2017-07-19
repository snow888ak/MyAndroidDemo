package com.example.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.retrofitdemo.entity.Repo;
import com.example.retrofitdemo.model.GitHubService;
import com.example.retrofitdemo.model.GitHubService2;
import com.example.retrofitdemo.sample1.SampleMainActivity;
import com.google.gson.Gson;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.content)
	TextView mTvContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BuildConfig.BaseUrl)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
						Log.d("Retrofit", "onResponse: response" + response.body().size());
					}

					@Override
					public void onFailure(Call<List<Repo>> call, Throwable t) {
						Log.d("Retrofit", "onFailure: Throwable" + new Gson().toJson(t));
					}
				});
			}
		});
		final GitHubService2 service2 = retrofit.create(GitHubService2.class);
		findViewById(R.id.btn_start2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				service2.listRepos("snow888ak")
						.subscribeOn(Schedulers.io())
						.flatMap(new Function<List<Repo>, ObservableSource<Repo>>() {
							@Override
							public ObservableSource<Repo> apply(@NonNull List<Repo> repos) throws Exception {
								return Observable.fromIterable(repos);
							}
						})
						.subscribeOn(AndroidSchedulers.mainThread())
						.map(new Function<Repo, String>() {
							@Override
							public String apply(@NonNull Repo repo) throws Exception {
								return repo.getHtml_url();
							}
						})
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Observer<String>() {
							@Override
							public void onSubscribe(@NonNull Disposable d) {
								clearMsg();
								printMsg("----onSubscribe----");
							}

							@Override
							public void onNext(@NonNull String s) {
								printMsg("----onNext----");
								printMsg("url：" + s);
							}

							@Override
							public void onError(@NonNull Throwable e) {
								printMsg("----onError----");
								printMsg(e.getMessage());
							}

							@Override
							public void onComplete() {
								printMsg("----onComplete----");
							}
						});
			}
		});
	}

	private void printMsg(String msg){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(msg).append("\n");
		mTvContent.append(sBuilder.toString());
	}

	private void clearMsg(){
		mTvContent.setText("");
	}

	@OnClick(R.id.btn_start3)
	public void toSampleActivity(View v){
		startActivity(new Intent(this, SampleMainActivity.class));
	}

}
