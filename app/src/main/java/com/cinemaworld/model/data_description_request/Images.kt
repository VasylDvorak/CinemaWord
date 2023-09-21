package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    @field:SerializedName("backdrops") var backdrops: List<Backdrop?>? = listOf(),
    @field:SerializedName("logos") var logos: List<Logo?>? = listOf(),
    @field:SerializedName("posters") var posters: List<Poster?>? = listOf()
) : Parcelable