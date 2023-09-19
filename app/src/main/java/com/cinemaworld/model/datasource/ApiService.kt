package com.cinemaworld.model.datasource

import com.cinemaworld.BuildConfig
import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

  //  https://api.themoviedb.org/3/search/multi?query=king&include_adult=false&language=en-US&page=1&api_key=274f828ad283bd634ef4fc1ee4af255f

   // https://api.themoviedb.org/3/movie/892527?api_key=274f828ad283bd634ef4fc1ee4af255f&append_to_response=credits,videos,images
// https://image.tmdb.org/t/p/w185/kEyi52oFS45X5sb78kAMnfrenxm.jpg


    @GET("search/multi")
    fun searchAsync(
        @Query("query") request_movie: String?="",
        @Query("include_adult") include_adult: Boolean?=false,
        @Query("page") page: Int?=1,
        @Query("api_key") api_key: String?="${BuildConfig.KEY}"
    ): Deferred<DataModel>

    @GET("movie/{id}")
    fun searchIdAsync(
        @Path("id") id:String?="550",
        @Query("api_key") api_key: String?="${BuildConfig.KEY}",
        @Query("append_to_response") append_to_response: String?="credits,videos,images"
    ): Deferred<DataModelId>
}
