package com.cinemaworld.model.repository

import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel

interface Repository{

    suspend fun getData(word: String): DataModel
    suspend fun getDataId(id: Int): DataModelId
}
