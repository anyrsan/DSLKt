package com.any.org.onemodule.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.get
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author any
 * @time 2019/11/25 10.46
 * @details
 */
class OneViewModel(private val oneRep: OneRepository) : BaseViewModel() {

    val isLoad = ObservableBoolean(true)

    //天气
    val weatherModel = MutableLiveData<OneDataWeatherModel>()

    //是否滑动顶部
    val isTop = MutableLiveData<Boolean>()

    //日期
    val mDate =  MutableLiveData<String>()

    //数据
    val contentList = MutableLiveData<List<OneDataItemModel>>()

    fun getOneData(
        isRefresh: Boolean = false,
        date: String = Calendar.getInstance().getTargetDate(),
        address: String = "深圳"
    ) {
        //开启加载
        if (isRefresh) {
            isLoad.set(true)
        }

        launch {
            oneRep.getOneData(date, address).async(300).doOnNext {

                contentList.set(it.data?.content_list)

                mDate.set(it.data?.date)

                weatherModel.set(it.data?.weather)

                isLoad.set(false)

                isTop.set(true)

            }.doOnError {

                isLoad.set(false)

            }.subscribe()
        }

    }


}