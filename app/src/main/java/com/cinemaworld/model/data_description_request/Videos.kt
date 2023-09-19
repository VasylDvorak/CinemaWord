package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    @field:SerializedName("results") var results: List<Result?>? = listOf()
) : Parcelable