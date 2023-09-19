package com.cinemaworld.model.loaders

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.utils.resultPairParser

typealias UsersPageLoader = suspend (nextPage: Int) -> DataModel

/**
 * Example implementation of [PagingSource].
 * It is used by [Pager] for fetching data.
 */
@Suppress("UnnecessaryVariable")

class FilmsPagingSource(
    private val loader: UsersPageLoader,
) : PagingSource<Int, Pair<Result?, Result?>>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<Result?, Result?>> {
        // get the index of page to be loaded (it may be NULL, in this case let's load the first page with index = 0)
        val pageIndex = params.key ?: 1
        return try {
            // loading the desired page of users
            val results = loader.invoke(pageIndex)
            // success! now we can return LoadResult.Page
           // println("pagepagepage "+ results.page!!)
            println("pageindexpageindex  " + pageIndex)
            return LoadResult.Page(
                data = resultPairParser(results.results as MutableList<Result?>),
                // index of the previous page if exists
                prevKey = if (pageIndex <= 1) null else pageIndex - 1,
                // index of the next page if exists;
                // please note that 'params.loadSize' may be larger for the first load (by default x3 times)
                nextKey = if (pageIndex >= results.total_pages!!) null else pageIndex + 1
            )
        } catch (e: Exception) {
            // failed to load users -> need to return LoadResult.Error
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<Result?, Result?>>): Int? {
       val anchorPosition =  state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}