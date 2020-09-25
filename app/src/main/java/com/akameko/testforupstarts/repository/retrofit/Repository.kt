package com.akameko.testforupstarts.repository.retrofit

import com.akameko.testforupstarts.repository.pojos.Jeans
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://static.upstarts.work/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val api: JeansApi

    val jeans: Single<List<Jeans>>
        get() = api.jeans

    init {
        api = retrofit.create(JeansApi::class.java)
    }
}