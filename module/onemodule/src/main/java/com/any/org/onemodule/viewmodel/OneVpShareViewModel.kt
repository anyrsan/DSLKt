package com.any.org.onemodule.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.extend.DateEx
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.*
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/25 10.46
 * @details  共享
 */
class OneVpShareViewModel(private val oneRep: OneRepository) : BaseViewModel() {

    val isLoad = ObservableBoolean(true)
    //天气
    val weatherModel = MutableLiveData<OneDataWeatherModel>()

    //是否滑动顶部
    val isTop = MutableLiveData<Boolean>()

    //日期
    val mDate = MutableLiveData<String>()

    private val _listDate = mutableListOf<String>()
    //所有日期
    val listDate = MutableLiveData<List<String>>()

    //数据集
    private val _mapContentData = mutableMapOf<String, List<OneDataItemModel>?>()

    //装载数据
    val mapContentData = MutableLiveData<MutableMap<String, List<OneDataItemModel>?>>()

    private fun getDefaultData(
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

                _mapContentData[date] = it.data?.content_list

                mapContentData.set(_mapContentData)

                mDate.set(it.data?.date)

                weatherModel.set(it.data?.weather)

                isLoad.set(false)

                isTop.set(true)

                KLog.e("默认数据 执行方法。。。。   ${it.data?.date}  ${it.data?.weather}")

            }.doOnError {

                isLoad.set(false)

            }.subscribe()
        }

    }


    /**
     * 获取指定日期对应的数据
     */
    fun getOneData(isRefresh: Boolean = false, date: String) {
        //如果加载的是当天，则要天气赋值
        if (DateEx.isToday(date)) {
            getDefaultData(true, date)
            return
        }

        //开启加载
        if (isRefresh) {
            isLoad.set(true)
        }

        launch {
            oneRep.getOneData(date, "深圳").async(300).doOnNext {

                _mapContentData[date] = it.data?.content_list

                mapContentData.set(_mapContentData)

                isTop.set(true)

                isLoad.set(false)

                KLog.e("处理数据。。。。 。。。。 。。。。 。。")

            }.doOnError {

                isLoad.set(false)

            }.subscribe()
        }

    }

    /***
     * 获取日期
     */
    fun getListDate(month: Int, isAdd: Boolean = false) {
        launch {
            if (!isAdd) {
                _listDate.clear()
            }
            oneRep.getListDate(month).async().subscribe {
                _listDate.addAll(it)
                listDate.set(_listDate)
            }
        }
    }


}