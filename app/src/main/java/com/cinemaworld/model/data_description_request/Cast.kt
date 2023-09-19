package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    @field:SerializedName("adult") var adult: Boolean? = false,
    @field:SerializedName("cast_id") var castId: Int? = 0,
    @field:SerializedName("character") var character: String? = "",
    @field:SerializedName("credit_id") var creditId: String? = "",
    @field:SerializedName("gender") var gender: Int? = 0,
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("known_for_department") var knownForDepartment: String? = "",
    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("order") var order: Int? = 0,
    @field:SerializedName("original_name") var originalName: String? = "",
    @field:SerializedName("popularity") var popularity: Double? = 0.0,
    @field:SerializedName("profile_path") var profilePath: String? = ""
) : Parcelable