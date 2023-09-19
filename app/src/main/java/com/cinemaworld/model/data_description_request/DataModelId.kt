package com.cinemaworld.model.data_description_request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class DataModelId(
    @field:SerializedName("adult") var adult: Boolean? = false,
    @field:SerializedName("backdrop_path") var backdropPath: String? = "",
    @field:SerializedName("belongs_to_collection") var belongsToCollection: @RawValue Any? = null,
    @field:SerializedName("budget") var budget: Int? = 0,
    @field:SerializedName("credits") var credits: Credits? = Credits(),
    @field:SerializedName("genres") var genres: List<Genre?>? = listOf(),
    @field:SerializedName("homepage") var homepage: String? = "",
    @field:SerializedName("id") var id: Int? = 0,
    @field:SerializedName("images") var images: Images? = Images(),
    @field:SerializedName("imdb_id") var imdbId: String? = "",
    @field:SerializedName("original_language") var originalLanguage: String? = "",
    @field:SerializedName("original_title") var originalTitle: String? = "",
    @field:SerializedName("overview") var overview: String? = "",
    @field:SerializedName("popularity") var popularity: Double? = 0.0,
    @field:SerializedName("poster_path") var posterPath: String? = "",
    @field:SerializedName("production_companies") var productionCompanies: List<ProductionCompany?>? = listOf(),
    @field:SerializedName("production_countries") var productionCountries: List<ProductionCountry?>? = listOf(),
    @field:SerializedName("release_date") var releaseDate: String? = "",
    @field:SerializedName("revenue") var revenue: Int? = 0,
    @field:SerializedName("runtime") var runtime: Int? = 0,
    @field:SerializedName("spoken_languages") var spokenLanguages: List<SpokenLanguage?>? = listOf(),
    @field:SerializedName("status") var status: String? = "",
    @field:SerializedName("tagline") var tagline: String? = "",
    @field:SerializedName("title") var title: String? = "",
    @field:SerializedName("video") var video: Boolean? = false,
    @field:SerializedName("videos") var videos: Videos? = Videos(),
    @field:SerializedName("vote_average") var voteAverage: Double? = 0.0,
    @field:SerializedName("vote_count") var voteCount: Int? = 0
) : Parcelable