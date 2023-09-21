package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(
    @field:SerializedName("cast") var cast: List<Cast?>? = listOf(),
    @field:SerializedName("crew") var crew: List<Crew?>? = listOf()
) : Parcelable