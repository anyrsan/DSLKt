package com.any.org.rxviewmodel.di

import com.any.org.rxviewmodel.model.local.LocalDataProvider
import com.any.org.rxviewmodel.model.repository.NewRepository
import com.any.org.rxviewmodel.viewmodel.DataViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author any
 * @time 2019/11/6 19.55
 * @details
 */

internal val localApi = module {
    single { LocalDataProvider() }
}

internal val repositoryApi = module {
    single { NewRepository(get()) }
}

internal val viewModellApi = module {
    viewModel {  DataViewModel(get()) }
}

internal val appModules = listOf(localApi, repositoryApi, viewModellApi)