package com.any.org.eanewsmudle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.set
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.model.repository.ANewsRepository

/**
 *
 * @author any
 * @time 2019/11/6 16.01
 * @details
 */
class RxYlViewModel(private val rep: ANewsRepository) : RxBaseViewModel() {


    val dataL = MutableLiveData<YLNewsModel>()


    private val map by lazy {
        mutableMapOf(
            "cateid" to "1Q",
            "cre" to "tianyi",
            "mod" to "pcent",
            "merge" to "3",
            "statics" to "1",
            "length" to "15",
            "up" to "0",
            "down" to "0"
        )
    }


    fun getLs() {
        launch {
            rep.ylList(map).async().subscribe(
                {
                    dataL.set(it)
                }, {


                })
        }
    }


}