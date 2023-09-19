package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("name") var name: String? = ""
) : Parcelable