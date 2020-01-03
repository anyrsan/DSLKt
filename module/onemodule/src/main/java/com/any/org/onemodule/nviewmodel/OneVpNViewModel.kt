package com.any.org.onemodule.nviewmodel

import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.set
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.OneDataItemModel
import com.any.org.onemodule.model.OneDataWeatherModel
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/25 10.46
 * @details  viewModel
 */
class OneVpNViewModel(private val oneRep: OneRepository) : BaseViewModel() {

    private val _listDate = mutableListOf<String>()
    //所有日期
    val listDate = MutableLiveData<List<String>>()

    val isLoad = MutableLiveData<Boolean>()
    //天气
    val weatherModel = MutableLiveData<OneDataWeatherModel>()
    //是否滑动顶部
    val isTop = MutableLiveData<Boolean>()
    //日期
    val mDate = MutableLiveData<String>()
    //装载数据
    val mapContentData = MutableLiveData<List<OneDataItemModel>>()

    //错误 页面的显示处理
    val errorShow = MutableLiveData<Boolean>()

    //处理计算
    val toPosition = MutableLiveData<Int>()





    /**
     * 获取天气
     */
    fun weaterData(date: String = Calendar.getInstance().getTargetDate(),
                   address: String = "深圳"){
        launch {
            oneRep.getOneData(date, address).async(300).doOnNext {

                mDate.set(it.data?.date)

                weatherModel.set(it.data?.weather)

            }.doOnError {

            }.subscribe()
        }

    }


    /**
     * 获取指定日期数据
     */
     fun getDefaultData(
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

                mapContentData.set(it.data?.content_list)

                isLoad.set(false)

                isTop.set(true)

                errorShow.set(false)

            }.doOnError {

                isLoad.set(false)

                errorShow.set(true)

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



    fun gotoPositionPage(date:String){
        launch {
            oneRep.getPosition(date,_listDate).async().subscribe { t1, t2 ->
                //说明没有找到，直接不处理
                 if(t1!=-1){
                     toPosition.set(t1)
                 }
            }
        }
    }







}