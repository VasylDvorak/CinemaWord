package com.cinemaworld.model.data_description_request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crew(
    @field:SerializedName("adult") var adult: Boolean? = false,
    @field:SerializedName("credit_id") var creditId: String? = "",
    @field:SerializedName("department") var department: String? = "",
    @field:SerializedName("gender") var gender: Int? = 0,
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("job") var job: String? = "",
    @field:SerializedName("known_for_department") var knownForDepartment: String? = "",
    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("original_name") var originalName: String? = "",
    @field:SerializedName("popularity") var popularity: Double? = 0.0,
    @field:SerializedName("profile_path") var profilePath: String? = ""
) : Parcelable