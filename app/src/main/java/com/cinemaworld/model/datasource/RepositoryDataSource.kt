package com.cinemaworld.model.datasource

import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel


interface RepositoryDataSource {

    suspend fun getDataId(id: String): DataModelId = DataModelId()

    suspend fun getData(word: String): DataModel = DataModel()
}
