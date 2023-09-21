package com.cinemaworld.model.data_word_request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @field:SerializedName("adult") var adult: Boolean? = false,
    @field:SerializedName("backdrop_path") var backdrop_path: String? = "",
    @field:SerializedName("first_air_date") var first_air_date: String? = "",
    @field:SerializedName("genre_ids") var genre_ids: List<Int?>? = listOf(),
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("media_type") var media_type: String? = "",
    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("origin_country") var origin_country: List<String?>? = listOf(),
    @field:SerializedName("original_language") var original_language: String? = "",
    @field:SerializedName("original_name") var original_name: String? = "",
    @field:SerializedName("original_title") var original_title: String? = "",
    @field:SerializedName("overview") var overview: String? = "",
    @field:SerializedName("popularity") var popularity: Double? = 0.0,
    @field:SerializedName("poster_path") var poster_path: String? = "",
    @field:SerializedName("release_date") var release_date: String? = "",
    @field:SerializedName("title") var title: String? = "",
    @field:SerializedName("video") var video: Boolean? = false,
    @field:SerializedName("vote_average") var vote_average: Double? = 0.0,
    @field:SerializedName("vote_count") var vote_count: Int? = 0
) : Parcelable