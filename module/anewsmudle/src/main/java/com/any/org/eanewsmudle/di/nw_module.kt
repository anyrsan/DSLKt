package com.any.org.eanewsmudle.di

import android.app.Application
import com.any.org.commonlibrary.net.NetManager
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.local.NewsLocalProvider
import com.any.org.eanewsmudle.model.remote.ANwNetProvider
import com.any.org.eanewsmudle.model.repository.NewsRepository
import com.any.org.eanewsmudle.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author any
 * @time 2019/10/31 15.29
 * @details
 */

val apiNModule = module {
    single { NetManager.create(ANwNetProvider.NewsApi::class.java) }
}

val netNProviderModule = module {
    single { ANwNetProvider(get()) }
}

val localNProviderModule = module {
    single { NewsLocalProvider() }
}

val repositoryN = module {
    single { NewsRepository(get(), get()) }
}

val viewNModels = module {
    viewModel { NewsViewModel(get()) }

    viewModel { (app: Application, item: NewsItemModel) -> TestViewModel(item, app, get()) }

    viewModel { ThsViewModel(get()) }

    viewModel { YLNewsViewModel(get()) }
}


val appNModules = listOf(apiNModule, netNProviderModule, localNProviderModule, repositoryN, viewNModels)