package com.cinemaworld.model.loaders.repositories
import androidx.paging.PagingData
import com.cinemaworld.model.data_word_request.Result
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    fun getPagedUsers(searchBy: String): Flow<PagingData<Pair<Result?, Result?>>>

}