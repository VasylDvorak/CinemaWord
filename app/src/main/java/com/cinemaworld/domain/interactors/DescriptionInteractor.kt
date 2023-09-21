package com.cinemaworld.domain.interactors

import android.content.Context
import com.cinemaworld.R
import com.cinemaworld.model.data_description_request.DescriptionAppState
import com.cinemaworld.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.mp.KoinPlatform.getKoin

class DescriptionInteractor(
    var repositoryRemote: Repository,
) {
    suspend fun getData(
        id: Int,
        fromRemoteSource: Boolean
    ): StateFlow<DescriptionAppState> {
        val descriptionAppState: DescriptionAppState
        if (fromRemoteSource) {
            descriptionAppState =
                DescriptionAppState.Success(repositoryRemote.getDataId(id))
        } else {
            val context = getKoin().get<Context>()
            descriptionAppState = DescriptionAppState.Error(
                Throwable(
                    context.getString(R.string.no_connection_internet)
                )
            )
        }
        return MutableStateFlow(descriptionAppState)
    }
}

