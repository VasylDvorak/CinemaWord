package com.cinemaworld.domain.interactors

import com.cinemaworld.model.datasource.AppState
import com.cinemaworld.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainInteractor(
    var repositoryRemote: Repository
) : Interactor<AppState> {

    override suspend fun getData(word: String, page: Int, fromRemoteSource: Boolean): StateFlow<AppState> {
            val remoteList = repositoryRemote.getData(word, page)
        return MutableStateFlow(AppState.Success(remoteList))
    }
}

