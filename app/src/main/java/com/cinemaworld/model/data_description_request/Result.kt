package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @field:SerializedName("id") var id: String? = "",
    @field:SerializedName("iso_3166_1") var iso31661: String? = "",
    @field:SerializedName("iso_639_1") var iso6391: String? = "",
    @field:SerializedName("key") var key: String? = "",
    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("official") var official: Boolean? = false,
    @field:SerializedName("published_at") var publishedAt: String? = "",
    @field:SerializedName("site") var site: String? = "",
    @field:SerializedName("size") var size: Int? = 0,
    @field:SerializedName("type") var type: String? = ""
) : Parcelable