package com.cinemaworld.di


import android.content.Context
import androidx.room.Room

import com.diplomproject.model.data_word_request.DataModel
import com.diplomproject.model.datasource.RetrofitImplementation
import com.diplomproject.model.repository.Repository
import com.diplomproject.model.repository.RepositoryImplementation
import com.diplomproject.model.repository.RepositoryImplementationLocal
import com.diplomproject.model.repository.RepositoryLocal
import com.diplomproject.domain.interactors.DescriptionInteractor
import com.diplomproject.domain.interactors.FavoriteInteractor
import com.diplomproject.domain.interactors.MainInteractor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin


const val dataBaseName = "HistoryDB"
const val favoriteScreenScopeName = "favoriteScreenScope"
const val mainScreenScopeName = "mainScreenScope"
const val descriptionScreenScopeName = "descriptionScreenScope"

object ConnectKoinModules {

    val application = module {
        single {
            Room.databaseBuilder(get(), FavoriteDataBase::class.java, dataBaseName).build()
        }
        single { get<FavoriteDataBase>().favoriteDao() }
        single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
        single<RepositoryLocal<List<DataModel>>> {
            RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
        }
        single { OnlineRepository() }
    }

    val favoriteScreen = module {
        scope(named<FavoriteFragment>()) {
            scoped { FavoriteInteractor(get()) }
            viewModel { FavoriteViewModel(get()) }
        }
    }
    val favoriteScreenScope by lazy {
        getKoin()
            .getOrCreateScope(favoriteScreenScopeName, named<FavoriteFragment>())
    }


    val mainScreen = module {
        scope(named<MainFragment>()) {
            scoped { MainInteractor(get(), get()) }
            viewModel { MainViewModel(get()) }
        }
    }

    val mainScreenScope by lazy {
        getKoin()
            .getOrCreateScope(mainScreenScopeName, named<MainFragment>())
    }

    val descriptionScreen = module {
        scope(named<DescriptionFragment>()) {
            scoped { DescriptionInteractor(get()) }
            viewModel { DescriptionViewModel(get()) }
        }
    }

    val descriptionScreenScope by lazy {
        getKoin()
            .getOrCreateScope(descriptionScreenScopeName, named<DescriptionFragment>())
    }


    val apiModule = module {
        factory { ApiModule().getService() }
    }

    val appModule = module {
        scope(named<Context>()) {
            scoped { AppModule().applicationContext(context = androidApplication()) }
        }
    }

    val ciceroneModule = module {

        single(qualifier = named(NAME_CICERONE_MODULE_CICERONE)) { CiceroneModule().cicerone() }
        single {
            CiceroneModule().navigatorHolder(
                cicerone =
                get(named(NAME_CICERONE_MODULE_CICERONE))
            )
        }
        single { CiceroneModule().router(cicerone = get(named(NAME_CICERONE_MODULE_CICERONE))) }
        single { CiceroneModule().screens() }
    }


    val mainFragmentModule = module {
        single { MainFragmentModule().mainFragment() }

    }

    val favoriteFragmentModule = module {
        single { FavoriteFragmentModule().favoriteFragment() }

    }

    val descriptionFragmentModule = module {
        single { DescriptionFragmentModule().descriptionFragment() }

    }
}