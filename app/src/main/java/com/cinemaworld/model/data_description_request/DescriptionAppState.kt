package com.cinemaworld.model.data_description_request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
sealed class DescriptionAppState : Parcelable {
    @Parcelize
    data class Success(val data: DataModelId?) : DescriptionAppState(), Parcelable

    @Parcelize
    data class Error(val error: Throwable) : DescriptionAppState(), Parcelable

    @Parcelize
    data class Loading(val progress: Int?) : DescriptionAppState(), Parcelable
}
