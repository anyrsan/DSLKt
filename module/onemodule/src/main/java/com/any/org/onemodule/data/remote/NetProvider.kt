package com.any.org.onemodule.data.remote

/**
 *
 * @author any
 * @time 2019/11/11 17.59
 * @details    这层可以去掉的，没有什么实际意义
 */
class NetProvider(private  val api: NetApi) {

    fun getOneData(date:String ,address:String ) = api.getOneData(date,address)

    fun <T> getOneArticleDetail(cateEnType:String,itemId:String) = api.getOneArticleDetail<T>(cateEnType,itemId)

    fun getMonthData(month:String) = api.getMonthData(month)



}