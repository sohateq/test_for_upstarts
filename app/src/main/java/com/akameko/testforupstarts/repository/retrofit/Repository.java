package com.akameko.testforupstarts.repository.retrofit;

import com.akameko.testforupstarts.repository.pojos.Jeans;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private Retrofit retrofit;
    private JeansApi api;

    public Repository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://static.upstarts.work/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(JeansApi.class);
    }


    public Single<List<Jeans>> getJeans() {
        return api.getJeans();
    }
}
