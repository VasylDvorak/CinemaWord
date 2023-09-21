package com.cinemaworld.model.datasource

import com.cinemaworld.BuildConfig
import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/multi")
    fun searchAsync(
        @Query("query") request_movie: String? = "",
        @Query("include_adult") include_adult: Boolean? = true,
        @Query("page") page: Int? = 1,
        @Query("api_key") api_key: String? = "${BuildConfig.KEY}"
    ): Deferred<DataModel>

    @GET("movie/{id}")
    fun searchIdAsync(
        @Path("id") id: String? = "550",
        @Query("api_key") api_key: String? = "${BuildConfig.KEY}",
        @Query("append_to_response") append_to_response: String? = "credits,videos,images"
    ): Deferred<DataModelId>
}
