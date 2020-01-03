package com.any.org.onemodule.nviewmodel

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
import com.any.org.onemodule.model.OneDataModel
import com.any.org.onemodule.model.OneMonthModel
import com.any.org.onemodule.model.OneMonthSubModel
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author any
 * @time 2019/11/25 10.46
 * @details 月分适配器
 */
class MonthViewModel(private val oneRep: OneRepository) : BaseViewModel() {

    val isLoad = ObservableBoolean(true)

    //设置数据
    val listDataMonths = SingleLiveEvent<MutableList<OneMonthModel>>()

    private val listData = mutableListOf<OneMonthModel>()

    //获取指定月分数据
    fun getMonthData(
        month: String = Calendar.getInstance().getTargetDate(1),
        isAdd: Boolean = false
    ) {
        launch {

            if(!isAdd){
                //show
                isLoad.set(true)
                listData.clear()
                //设置数据为空数组
                listDataMonths.set(listData)
            }

            oneRep.getMonthData(month).async(300).doAfterSuccess {
                it.date = month
            }.subscribe { t1, t2 ->
                //添加数据
                listData.add(t1)
                //设置更新
                listDataMonths.set(listData)
                // t2 是出错了
                isLoad.set(false)
            }
        }
    }


}