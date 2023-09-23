package com.cinemaworld.utils

import com.cinemaworld.BuildConfig
import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_description_request.DescriptionAppState
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.data_word_request.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


fun resultPairParser(results: MutableList<Result?>): MutableList<Pair<Result?, Result?>> {
    var outputResults: MutableList<Pair<Result?, Result?>> = mutableListOf()
    var searchResults = results

    if (!results.isNullOrEmpty()) {
        if (searchResults.size % 2 != 0) {
            searchResults.add(null)
        }
        var counter = 0
        do {
            outputResults.add(Pair(searchResults[counter], searchResults[++counter]))
            ++counter
        } while (counter < (searchResults.size - 1))

    }
    return outputResults
}

suspend fun parseSearchResultsDescription(state: Flow<DescriptionAppState>): DescriptionAppState {
    var descriptionAppState = state.first()


    when (descriptionAppState) {
        is DescriptionAppState.Success -> {
            var searchResults = descriptionAppState.data!!
            descriptionAppState = DescriptionAppState.Success(searchResults)
        }

        else -> {}
    }
    return descriptionAppState
}


fun dataModelParserAddImageURL(output: DataModel): DataModel {

    if (!output.results.isNullOrEmpty()) {
        output.results = output.results!!.filter {
            !(it?.poster_path.isNullOrEmpty()
                    || it?.poster_path.isNullOrBlank()
                    || it?.backdrop_path.isNullOrBlank()
                    || it?.backdrop_path.isNullOrEmpty())
        }

        output.results!!.forEach { result ->
            result?.backdrop_path = BuildConfig.IMAGE_URL + result?.backdrop_path
            result?.poster_path = BuildConfig.IMAGE_URL + result?.poster_path
        }
    }
    return output
}

fun dataModelIdParserAddImageURL(output: DataModelId): DataModelId {
    output.backdropPath = BuildConfig.IMAGE_URL + output.backdropPath
    output.posterPath = BuildConfig.IMAGE_URL + output.posterPath

    if (!output.productionCompanies.isNullOrEmpty()) {
        output.productionCompanies!!.forEach { result ->
            result?.logoPath?.let { result.logoPath = BuildConfig.IMAGE_URL + result.logoPath }
        }
    }

    if (!output.credits?.cast.isNullOrEmpty()) {
        output.credits?.cast!!.forEach { result ->
            result?.profilePath?.let {
                result.profilePath = BuildConfig.IMAGE_URL + result.profilePath
            }
        }
    }

    if (!output.credits?.crew.isNullOrEmpty()) {
        output.credits?.crew!!.forEach { result ->
            result?.profilePath?.let {
                result.profilePath = BuildConfig.IMAGE_URL + result.profilePath
            }
        }
    }

    if (!output.images?.backdrops.isNullOrEmpty()) {
        output.images?.backdrops!!.forEach { result ->
            result?.filePath?.let { result.filePath = BuildConfig.IMAGE_URL + result.filePath }
        }
    }

    if (!output.images?.logos.isNullOrEmpty()) {
        output.images?.logos!!.forEach { result ->
            result?.filePath?.let { result.filePath = BuildConfig.IMAGE_URL + result.filePath }
        }
    }

    if (!output.images?.posters.isNullOrEmpty()) {
        output.images?.posters!!.forEach { result ->
            result?.filePath?.let { result.filePath = BuildConfig.IMAGE_URL + result.filePath }
        }
    }

    return output
}
