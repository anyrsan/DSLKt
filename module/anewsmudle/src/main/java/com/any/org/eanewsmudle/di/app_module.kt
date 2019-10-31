package com.any.org.eanewsmudle.di

import android.app.Application
import com.any.org.commonlibrary.net.NetManager
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.model.local.NewsLocalProvider
import com.any.org.eanewsmudle.model.remote.ANwNetProvider
import com.any.org.eanewsmudle.model.remote.NewsNetProvider
import com.any.org.eanewsmudle.model.repository.NewsRepository
import com.any.org.eanewsmudle.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author any
 * @time 2019/10/28 10.24
 * @details 通过 koin 处理注入
 */


val apiModule = module {

    //完成自动注入
    single { NetManager.create(NewsNetProvider.NewsApi::class.java) }

    single { NetManager.create(ANwNetProvider.NewsApi::class.java) }

}


val netProviderModule = module {
    //区别，如果有多个时  <T> ，加上泛型
    single { NewsNetProvider(get()) }

    single { ANwNetProvider(get()) }
}

val localProviderModule = module {

    single { NewsLocalProvider() }

}

val repository = module {

    single { NewsRepository(get(), get()) }

}

val viewModels = module {
    viewModel { NewsViewModel(get()) }

    viewModel { (app: Application, item: NewsItemModel) -> TestViewModel(item, app, get()) }

    viewModel { ThsViewModel(get()) }

    viewModel { YLNewsViewModel(get()) }
}


val appModules = listOf(apiModule, netProviderModule, localProviderModule, repository, viewModels)
