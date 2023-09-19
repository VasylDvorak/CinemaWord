package com.cinemaworld.domain.interactors

import com.cinemaworld.model.datasource.AppState
import com.cinemaworld.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainInteractor(
    var repositoryRemote: Repository,
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): StateFlow<AppState> {
        var appState: AppState
            val remoteList = repositoryRemote.getData(word)
            appState = AppState.Success(remoteList)
        return MutableStateFlow(appState)
    }
}

