package com.cinemaworld.model.repository

import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.model.datasource.RepositoryDataSource

class RepositoryImplementation(private val dataSource: RepositoryDataSource) :
    Repository {

    override suspend fun getData(word: String, page:Int): DataModel {
        return dataSource.getData(word, page)
    }

    override suspend fun getDataId(id: Int): DataModelId {
        return dataSource.getDataId(id.toString())
    }

}

