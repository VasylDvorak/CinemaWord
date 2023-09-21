package com.cinemaworld.model.data_word_request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    @field:SerializedName("page")  var page: Int? = 0,
    @field:SerializedName("results") var results: List<Result?>? = listOf(),
    @field:SerializedName("total_pages") var total_pages: Int? = 0,
    @field:SerializedName("total_results") var total_results: Int? = 0
) : Parcelable