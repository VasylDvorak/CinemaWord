package com.cinemaworld.di


import android.content.Context
import com.cinemaworld.di.koin_modules.ApiModule
import com.cinemaworld.di.koin_modules.AppModule
import com.cinemaworld.di.koin_modules.DescriptionFragmentModule
import com.cinemaworld.di.koin_modules.MainFragmentModule
import com.cinemaworld.domain.interactors.DescriptionInteractor
import com.cinemaworld.model.datasource.RetrofitImplementation
import com.cinemaworld.model.loaders.repositories.FilmsRetrofitRepository
import com.cinemaworld.model.repository.OnLineRepository
import com.cinemaworld.model.repository.Repository
import com.cinemaworld.model.repository.RepositoryImplementation
import com.cinemaworld.views.MainViewModel
import com.cinemaworld.views.description.DescriptionFragment
import com.cinemaworld.views.description.DescriptionViewModel
import com.cinemaworld.views.main_fragment.MainFragment
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin


const val mainScreenScopeName = "mainScreenScope"
const val descriptionScreenScopeName = "descriptionScreenScope"

object ConnectKoinModules {

    val application = module {
        single<Repository> { RepositoryImplementation(RetrofitImplementation()) }
        single { OnLineRepository() }
    }


    val mainScreen = module {
        scope(named<MainFragment>()) {
            viewModel { MainViewModel(FilmsRetrofitRepository(Dispatchers.IO)) }
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


    val mainFragmentModule = module {
        single { MainFragmentModule().mainFragment() }

    }


    val descriptionFragmentModule = module {
        single { DescriptionFragmentModule().descriptionFragment() }

    }
}