package com.cinemaworld.model.loaders.repositories


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cinemaworld.domain.interactors.MainInteractor
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.model.datasource.AppState
import com.cinemaworld.model.datasource.RetrofitImplementation
import com.cinemaworld.model.repository.RepositoryImplementation
import com.cinemaworld.model.loaders.UsersPageLoader
import com.cinemaworld.model.loaders.FilmsPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class FilmsRetrofitRepository(
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {
    val interactor =  MainInteractor(RepositoryImplementation(RetrofitImplementation()))
    override fun getPagedUsers(searchBy: String): Flow<PagingData<Pair<Result?, Result?>>> {
        val loader: UsersPageLoader = { nextPage ->
         dataModel(searchBy, nextPage, true)  as DataModel
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FilmsPagingSource(loader) }
        ).flow
    }


    private suspend fun dataModel(word: String, page: Int, isOnline: Boolean): DataModel? {
       val appState =  getResults(word, page, isOnline)

        when (appState) {
            is AppState.Success -> {
                return appState.data
            }

            else -> {return null}
        }
    }


    private suspend  fun getResults(word: String, page: Int, isOnline: Boolean): AppState
        = withContext(ioDispatcher) {
        return@withContext interactor.getData(word, page, isOnline).first()
    }

    private companion object {
        const val PAGE_SIZE = 15
    }
}