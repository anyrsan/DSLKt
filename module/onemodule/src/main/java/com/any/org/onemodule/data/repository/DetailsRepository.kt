package com.any.org.onemodule.data.repository

import com.any.org.onemodule.data.remote.DetailsApi

/**
 *
 * @author any
 * @time 2019/12/12 19.05
 * @details 详情数据获取
 */
class DetailsRepository(private val api: DetailsApi) {

    suspend fun getEssayDetail(cateEn: String, itemId: String) = api.getEssayDetail(cateEn, itemId)

    suspend fun getSerialDetail(cateEn: String, itemId: String) =
        api.getSerialDetail(cateEn, itemId)

    suspend fun getQuestionModel(cateEn: String, itemId: String) =
        api.getQuestionModel(cateEn, itemId)

    suspend fun getComment(cateEn: String, contentId: String, commentId: String) =
        api.getComment(cateEn, contentId, commentId)

    suspend fun getSerialMenu(yearMonth: String) = api.getSerialMenu(yearMonth)

}