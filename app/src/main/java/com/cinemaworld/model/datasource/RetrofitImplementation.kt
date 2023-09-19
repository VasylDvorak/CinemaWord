package com.cinemaworld.model.datasource


import com.cinemaworld.model.data_description_request.DataModelId
import com.cinemaworld.model.data_word_request.DataModel
import com.cinemaworld.utils.dataModelIdParserAddImageURL
import com.cinemaworld.utils.dataModelParserAddImageURL
import org.koin.java.KoinJavaComponent.getKoin


class RetrofitImplementation : RepositoryDataSource {

    override suspend fun getData(word: String, page:Int): DataModel {
        val getService = getKoin().get<ApiService>()
        return dataModelParserAddImageURL(getService.searchAsync(word, page=page).await())
    }

    override suspend fun getDataId(id: String): DataModelId {
        val getService = getKoin().get<ApiService>()
        return dataModelIdParserAddImageURL(getService.searchIdAsync(id).await())
    }

}
