package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpokenLanguage(
    @field:SerializedName("english_name") var englishName: String? = "",
    @field:SerializedName("iso_639_1") var iso6391: String? = "",
    @field:SerializedName("name") var name: String? = ""
) : Parcelable