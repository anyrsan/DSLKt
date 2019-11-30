package com.any.org.onemodule.di

import androidx.room.Room
import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetApi
import com.any.org.onemodule.data.remote.NetProvider
import com.any.org.onemodule.data.remote.TestApi
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.data.room.ArticleDatabase
import com.any.org.onemodule.net.OneNetManager
import com.any.org.onemodule.viewmodel.OneVM
import com.any.org.onemodule.viewmodel.OneViewModel
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
        OneNetManager.create(TestApi::class.java)
    }

}

val remoteModule = module {

    single {
        NetProvider(get())
    }

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
}


val viewModels = module {
    viewModel {
        OneViewModel(get())
    }


    viewModel {
        OneVM(get())
    }
}


val appModules = listOf(roomModule, apiModule, remoteModule, localModule, repModule, viewModels)