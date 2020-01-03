package com.any.org.onemodule.nviewmodel

import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.repository.DetailsRepository
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.MenuModel
import com.any.org.onemodule.model.SerialMenuModel
import java.util.*

/**
 *
 * @author any
 * @time 2020/1/2 15.38
 * @details  菜单类型
 */
class NMenuViewModel(private val rep: DetailsRepository) : NBaseCoroutineViewModel() {

    //菜单数据项目
    val listMenu = SingleLiveEvent<List<MenuModel>>()

    //获取当前月分连载
    fun getSerialMenu(itemId: String,yearMonth: String = Calendar.getInstance().getTargetDate(1)) {
        doTask {
            val model = rep.getSerialMenu(yearMonth)
            //启动新协程
            doTask {
                val list = executiveRequest {
                    convertData(model,itemId)
                }
                KLog.e("输出处理好的数据。。。 $list")
                //数据进行保存
                listMenu.set(list)
            }
        }
    }


    //数据转换
    private fun convertData(model: SerialMenuModel, itemId: String): List<MenuModel> {
        //第一个肯定是最新的数据
        val newData= model.data.first {
            it.id == itemId
        }
        val listMenu = mutableListOf<MenuModel>()
        newData.serial_list.forEach {
            val model = MenuModel()
            model.page = it
            model.isShow = it==itemId
            listMenu.add(model)
        }

        return listMenu
    }

}