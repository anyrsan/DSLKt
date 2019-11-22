package com.any.org.onemodule.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author any
 * @time 2019/11/16 14.25
 * @details
 */
abstract class BaseViewModel :ViewModel(){

    private val disposables by lazy { CompositeDisposable() }

    fun launch(job:()->Disposable){
        disposables.add(job())
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}