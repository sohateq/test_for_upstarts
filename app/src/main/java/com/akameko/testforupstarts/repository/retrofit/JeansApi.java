package com.akameko.testforupstarts.repository.retrofit;

import androidx.room.Dao;

import com.akameko.testforupstarts.repository.pojos.Jeans;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
@Dao
public interface JeansApi {
    @GET("tests/jeans/jeans-default.json")
    Single<List<Jeans>> getJeans();
}
