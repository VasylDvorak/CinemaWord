package com.cinemaworld.domain.interactors


import kotlinx.coroutines.flow.Flow

interface Interactor<T : Any> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): Flow<T>

}