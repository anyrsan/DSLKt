package com.any.org.onemodule.nviewmodel

import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.data.repository.DetailsRepository
import com.any.org.onemodule.extend.StringEx
import com.any.org.onemodule.model.ContentDetailsModel

/**
 *
 * @author any
 * @time 2019/12/12 19.11
 * @details 连载详情页
 */
class NSericalViewModel(private val rep: DetailsRepository) : NDetailsViewModel(rep) {

    override fun getDetailsContent(category: String?, itemId: String) {
        showAuthor.set(true)
        doTask {
            val model = executiveRequest {
                rep.getSerialDetail(CateApi.getCateEn(category), itemId)
            }
            var cId: String? = null
            model?.data?.apply {
                cContent.set(content)
                cTitle.set(title)
                cAnchor.set(author.user_name)
                progress.set(StringEx.formatTime(audio_duration))
                baseModel.set(this as ContentDetailsModel)
                isMenu.set(true)
                cId = id
            }
            //延后加载
            commentType = "serial"
            doDelayTask(timeMillis = 1000) {
                KLog.e("AAA","getComment.... 开始加载")
                cId?.let {
                    contentId = it
                    lastCommentId = "0"
                    getComment(it, lastCommentId)
                }
            }
        }
    }




}