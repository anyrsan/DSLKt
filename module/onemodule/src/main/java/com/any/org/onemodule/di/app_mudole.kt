package com.any.org.onemodule.di

import androidx.room.Room
import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetApi
import com.any.org.onemodule.data.remote.DetailsApi
import com.any.org.onemodule.data.repository.DetailsRepository
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.data.room.ArticleDatabase
import com.any.org.onemodule.net.OneNetManager
import com.any.org.onemodule.nviewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author any
 * @time 2019/11/11 20.01
 * @details
 */

val roomModule = module {

    // database
    single {
        Room.databaseBuilder(androidContext(), ArticleDatabase::class.java, "art_db").build()
    }

    // 获取数据库对象
    single {
        get<ArticleDatabase>().articleDao()
    }

}

val apiModule = module {

    //netApi
    single {
        OneNetManager.create(NetApi::class.java)
    }

    single {
        OneNetManager.create(DetailsApi::class.java)
    }

}

val remoteModule = module {


}


val localModule = module {
    single {
        LocalProvider(get())
    }
}


val repModule = module {
    single {
        OneRepository(get(), get())
    }

    single {
        DetailsRepository(get())
    }
}


val viewModels = module {
    viewModel {
        MonthViewModel(get())
    }

    viewModel {
        OneVpNViewModel(get())
    }

    viewModel {
        NSericalViewModel(get())
    }

    viewModel {
        NQtViewModel(get())
    }

    viewModel {
        NDetailsViewModel(get())
    }

    viewModel {
        NMenuViewModel(get())
    }

    viewModel {
        TestViewModel()
    }

}


val appModules = listOf(roomModule, apiModule, remoteModule, localModule, repModule, viewModels)