package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Poster(
    @field:SerializedName("aspect_ratio") var aspectRatio: Double? = 0.0,
    @field:SerializedName("file_path") var filePath: String? = "",
    @field:SerializedName("height") var height: Int? = 0,
    @field:SerializedName("iso_639_1") var iso6391: String? = "",
    @field:SerializedName("vote_average") var voteAverage: Double? = 0.0,
    @field:SerializedName("vote_count") var voteCount: Int? = 0,
    @field:SerializedName("width") var width: Int? = 0
) : Parcelable