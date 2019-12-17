package com.any.org.onemodule.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.runUI
import com.any.org.onemodule.adapter.obser.AdapterDataObserver
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.data.repository.DetailsRepository
import com.any.org.onemodule.extend.StringEx
import com.any.org.onemodule.manager.VoicePlayerManager
import com.any.org.onemodule.model.CommentItemModel
import com.any.org.onemodule.model.ContentDetailsModel
import com.any.org.onemodule.viewevent.NDViewClick

/**
 *
 * @author any
 * @time 2019/12/12 19.11
 * @details
 */
class DetailsViewModel(private val rep: DetailsRepository) : BaseCoroutineViewModel() {

    //设置初始值
    val baseModel = MutableLiveData<ContentDetailsModel>()

    val cTitle = MutableLiveData<String>()

    val cContent = MutableLiveData<String>()

    val cAnchor = MutableLiveData<String>()

    val commentList = AdapterDataObserver<CommentItemModel>()

    val progress = MutableLiveData<String>()

    val animPlay = MutableLiveData<Boolean>()

    val isMenu = MutableLiveData<Boolean>()

    private var lastCommentId = "0"

    private var contentId = "0"

    private var contentType = ""

    //处理事件
    val ndClick = object : NDViewClick {
        override fun clickView(view: View) {
            val url = baseModel.value?.audio
            KLog.e("msg...   url= $url")
            url?.let {
                val isPlaying = VoicePlayerManager.vPlayer.isPlayIng(it)
                if (isPlaying) {
                    VoicePlayerManager.vPlayer.stopPlay()
                    return
                }
                VoicePlayerManager.vPlayer.startPlay(it, ::onPlayState, ::onPlayProgress)
            }
        }
    }


    init {
        VoicePlayerManager.vPlayer.onStopCallBack = {
            animPlay.set(false)
            progress.set(StringEx.formatTime(baseModel.value?.audio_duration))
        }
    }


    private fun onPlayState(playStart: Boolean, isError: Boolean, isCompletion: Boolean) {
        animPlay.set(playStart)
        KLog.e("onPlayState...  $playStart  $isError  $isCompletion")
    }


    private fun onPlayProgress(progress: String) {
        this.progress.set(progress)
        KLog.e("progress...  $progress")
    }


    fun getDetailsContent(category: String?, itemId: String) {
        when (category) {
            "0" -> {

            }
            "1" -> {
                doTask({
                    it.printStackTrace()
                    KLog.e("msg... $it")
                }) {
                    val model = executiveRequest {
                        rep.getEssayDetail(CateApi.getCateEn(category), itemId)
                    }
                    var cId: String? = null
                    model?.data?.apply {
                        cContent.set(hp_content)
                        cTitle.set(hp_title)
                        cAnchor.set(hp_author)
                        baseModel.set(this as ContentDetailsModel)
                        cId = content_id
                        progress.set(StringEx.formatTime(audio_duration))
                    }
                    //延后加载
                    doDelayTask(timeMillis = 1000) {
                        KLog.e("getComment.... 开始加载")
                        contentType = CateApi.getCateEn(category)
                        cId?.let {
                            contentId = it
                            lastCommentId = "0"
                            getComment(it, lastCommentId)
                        }
                    }
                }
            }
            "2" -> {
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
                    contentType = "serial"
                    doDelayTask(timeMillis = 1000) {
                        KLog.e("getComment.... 开始加载")
                        cId?.let {
                            contentId = it
                            lastCommentId = "0"
                            getComment(it, lastCommentId)
                        }
                    }
                }
            }
            "3" -> {

            }
            "4" -> {

            }
            "5" -> {

            }
        }
    }


    fun getComment() {
        KLog.e("getComment.... next....   $contentId")
        getComment(contentId, lastCommentId)
    }

    private fun getComment(contentId: String, commentId: String) {
        doTask {
            val model = executiveRequest {
                rep.getComment(contentType, contentId, commentId)
            }
            model?.let {
                commentList.updateData(it.data.data, commentId == "0")
                lastCommentId = it.data.data.last().id
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        VoicePlayerManager.vPlayer.onStopCallBack = null
        commentList.onCleared()
    }


    fun onDestroy(){
        onCleared()
    }

}