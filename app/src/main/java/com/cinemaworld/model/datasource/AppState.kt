package com.cinemaworld.model.datasource

import android.os.Parcelable
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.data_word_request.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
sealed class AppState : Parcelable {
    @Parcelize
    data class Success(val data: DataModel?) : AppState(), Parcelable
    @Parcelize
    data class SuccessPair(val dataPair:MutableList<Pair<Result?, Result?>> ) :
        AppState(), Parcelable

    @Parcelize
    data class Error(val error: Throwable) : AppState(), Parcelable

    @Parcelize
    data class Loading(val progress: Int?) : AppState(), Parcelable
}
