package com.any.org.onemodule.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.get
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.OneDataModel
import com.any.org.onemodule.model.OneMonthModel
import com.any.org.onemodule.model.OneMonthSubModel
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

    val dataModel = MutableLiveData<OneDataModel>()

    val dataMonths = MutableLiveData<ArrayList<OneMonthModel>>()

    //处理多个
    private val listMons = ArrayList<OneMonthModel>()

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

                dataModel.set(it)

                isLoad.set(false)

            }.doOnError {

                isLoad.set(false)

            }.subscribe()
        }

    }


    //获取指定月分数据
    fun getMonthData(
        month: String = Calendar.getInstance().getTargetDate(1),
        isAdd: Boolean = false
    ) {
        launch {
            //清除列表
            if(!isAdd){
                listMons.clear()
            }
            oneRep.getMonthData(month).async(300).doAfterSuccess {
                it.date = month
            }.subscribe { t1, t2 ->
                // 处理值
                listMons.add(t1)
                dataMonths.set(listMons)
                // t2 是出错了
            }
        }
    }


}