package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompany(
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("logo_path") var logoPath: String? = "",
    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("origin_country") var originCountry: String? = ""
) : Parcelable