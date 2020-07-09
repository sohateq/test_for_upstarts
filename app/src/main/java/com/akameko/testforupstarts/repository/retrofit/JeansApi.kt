package com.akameko.testforupstarts.repository.retrofit

import androidx.room.Dao
import com.akameko.testforupstarts.repository.pojos.Jeans
import io.reactivex.Single
import retrofit2.http.GET

@Dao
interface JeansApi {
    @get:GET("tests/jeans/jeans-default.json")
    var jeans: Single<List<Jeans>>
}