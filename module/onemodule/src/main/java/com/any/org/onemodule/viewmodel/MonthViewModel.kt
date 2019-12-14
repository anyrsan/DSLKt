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
class MonthViewModel(private val oneRep: OneRepository) : BaseViewModel() {

    val isLoad = ObservableBoolean(true)

    val dataMonths = SingleLiveEvent<OneMonthModel>()

    //获取指定月分数据
    fun getMonthData(
        month: String = Calendar.getInstance().getTargetDate(1),
        isAdd: Boolean = false
    ) {
        launch {

            if(!isAdd){
                //show
                isLoad.set(true)
            }

            oneRep.getMonthData(month).async(300).doAfterSuccess {
                it.date = month
            }.subscribe { t1, t2 ->
                //获取值
                dataMonths.set(t1)
                // t2 是出错了
                isLoad.set(false)
            }
        }
    }


}