package com.any.org.onemodule.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.OneDataModel
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/25 10.46
 * @details
 */
class OneViewModel(private val oneRep: OneRepository) : BaseViewModel() {


    val isLoad = ObservableBoolean()

    val dataModel = MutableLiveData<OneDataModel>()


    fun getOneData(
        isRefrsh: Boolean = false,
        date: String = Calendar.getInstance().getTargetDate(),
        address: String = "深圳"
    ) {
        //开启加载
        if (isRefrsh) {
            isLoad.set(true)
        }

        launch {
            oneRep.getOneData(date, address).async(300).doOnNext {

                dataModel.set(it)

                isLoad.set(false)

            }.doOnError {

                isLoad.set(false)

            }.subscribe()
        }

    }

}