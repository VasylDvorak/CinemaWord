package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCountry(
    @field:SerializedName("iso_3166_1") var iso31661: String? = "",
    @field:SerializedName("name") var name: String? = ""
) : Parcelable