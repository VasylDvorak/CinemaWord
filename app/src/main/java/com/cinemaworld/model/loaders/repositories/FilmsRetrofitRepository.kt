package com.cinemaworld.model.loaders.repositories


import android.content.Context
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cinemaworld.R
import com.cinemaworld.domain.interactors.MainInteractor
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.model.datasource.AppState
import com.cinemaworld.model.datasource.RetrofitImplementation
import com.cinemaworld.model.loaders.FilmsPagingSource
import com.cinemaworld.model.loaders.UsersPageLoader
import com.cinemaworld.model.repository.RepositoryImplementation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform.getKoin

class FilmsRetrofitRepository(
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {
    val interactor = MainInteractor(RepositoryImplementation(RetrofitImplementation()))
    override fun getPagedUsers(searchBy: String): Flow<PagingData<Pair<Result?, Result?>>> {
        val loader: UsersPageLoader = { nextPage ->
            dataModel(searchBy, nextPage, true) as DataModel
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
        val appState = getResults(word, page, isOnline)

        when (appState) {
            is AppState.Success -> {
                val output = appState.data
                if ((output?.results.isNullOrEmpty()) && (word.isNotEmpty())) {
                    val contextApp = getKoin().get<Context>()
                    Toast.makeText(
                        contextApp, contextApp.getString(R.string.cant_find_film) + " " + word,
                        Toast.LENGTH_LONG
                    ).show()
                }
                return output
            }
            else -> {
                return null
            }
        }
    }


    private suspend fun getResults(word: String, page: Int, isOnline: Boolean): AppState =
        withContext(ioDispatcher) {
            return@withContext interactor.getData(word, page, isOnline).first()
        }

    private companion object {
        const val PAGE_SIZE = 15
    }
}