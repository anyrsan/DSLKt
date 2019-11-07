package com.any.org.eanewsmudle.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author any
 * @time 2019/11/6 15.58
 * @details
 */
abstract class RxBaseViewModel : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    fun launch(job:()->Disposable){
        disposables.add(job())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


}