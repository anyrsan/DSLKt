package com.any.org.onemodule.nviewmodel

import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.data.repository.DetailsRepository
import com.any.org.onemodule.model.ContentDetailsModel

/**
 *
 * @author any
 * @time 2019/12/12 19.11
 * @details 问答处理
 */
class NQtViewModel(private val rep: DetailsRepository) : NDetailsViewModel(rep) {

    /***
     *   重写 获取问答
     */
    override fun getDetailsContent(category: String?, itemId: String) {
        isQt.set(true)
        doTask {
            val model = executiveRequest {
                rep.getQuestionModel(CateApi.getCateEn(category), itemId)
            }
            var cId: String? = null
            model?.data?.apply {
                askerName.set(asker.user_name)
                answererName.set(answerer.user_name)
                cContent.set(answer_content)
                cTitle.set(question_title)
                baseModel.set(this as ContentDetailsModel)
                cId = question_id
            }
            //延后加载
            commentType = "question"
            doDelayTask(timeMillis = 1000) {
                KLog.e("getComment.... 开始加载  $cId")
                cId?.let {
                    contentId = it
                    lastCommentId = "0"
                    getComment(it, lastCommentId)
                }
            }
        }


    }


}