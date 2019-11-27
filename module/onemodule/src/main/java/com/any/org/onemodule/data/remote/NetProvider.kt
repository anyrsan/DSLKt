package com.any.org.onemodule.data.remote

/**
 *
 * @author any
 * @time 2019/11/11 17.59
 * @details
 */
class NetProvider(private  val api: NetApi) {

    fun getOneData(date:String ,address:String ) = api.getOneData(date,address)

    fun getOneArticleDetail(cateEnType:String,itemId:String) = api.getOneArticleDetail(cateEnType,itemId)

    fun getMonthData(month:String) = api.getMonthData(month)



}