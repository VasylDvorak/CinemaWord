package com.cinemaworld.domain.interactors


import kotlinx.coroutines.flow.Flow

interface Interactor<T : Any> {

    suspend fun getData(word: String, page: Int, fromRemoteSource: Boolean): Flow<T>

}