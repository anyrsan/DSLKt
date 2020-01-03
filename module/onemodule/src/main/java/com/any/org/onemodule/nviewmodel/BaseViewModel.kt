package com.any.org.onemodule.nviewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author any
 * @time 2019/11/16 14.25
 * @details  RxJava 处理
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