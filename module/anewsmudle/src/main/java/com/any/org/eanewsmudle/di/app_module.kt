package com.any.org.eanewsmudle.di

import com.any.org.commonlibrary.net.NetManager
import com.any.org.eanewsmudle.model.local.ANewsLocalProvider
import com.any.org.eanewsmudle.model.remote.ARemoteProvider
import com.any.org.eanewsmudle.model.repository.ANewsRepository
import com.any.org.eanewsmudle.viewmodel.AMainViewModel
import com.any.org.eanewsmudle.viewmodel.SnViewModel
import com.any.org.eanewsmudle.viewmodel.ThsViewModel
import com.any.org.eanewsmudle.viewmodel.YlViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author any
 * @time 2019/10/28 10.24
 * @details 通过 koin 处理注入
 */
//  single 是 单例模式
//  factory 是 new对象
val apiModule = module {
    single { NetManager.create(ARemoteProvider.NewsApi::class.java) }
}

//远程提供
val remoteProviderModule = module {
    single { ARemoteProvider(get()) }
}

//本地提供
val localProviderModule = module {
    single { ANewsLocalProvider() }
}

// 仓库处理
val repository = module {
    single { ANewsRepository(get(), get()) }
}

//  多个参数时，可以定义 表达式 通地 -> 定义，实际调用上，在构建viewmodel时，传入相应参数 ，详情见 TestActivity
//  定义 viewModel { (app:Application,item:NewsItemModel) ->  TestViewModel(item,app,get())}
//  viewModel<TestViewModel> { parametersOf(application,itemModel) }

val viewModels = module {
    viewModel { AMainViewModel() }
    viewModel { SnViewModel(get()) }
    viewModel { ThsViewModel(get()) }
    viewModel { YlViewModel(get()) }
}


val appModules =
    listOf(apiModule, remoteProviderModule, localProviderModule, repository, viewModels)
