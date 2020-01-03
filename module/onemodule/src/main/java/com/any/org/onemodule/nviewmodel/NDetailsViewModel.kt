package com.any.org.onemodule.nviewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
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
 * @details 内容详情（基础类，主要定义了公共数据）
 */
open class NDetailsViewModel(private val rep: DetailsRepository) : NBaseCoroutineViewModel() {

    //设置初始值 基础数据
    val baseModel = MutableLiveData<ContentDetailsModel>()
    //标题
    val cTitle = MutableLiveData<String>()
    //正文
    val cContent = MutableLiveData<String>()
    //作者
    val cAnchor = MutableLiveData<String>()
    //评论
    val commentList = AdapterDataObserver<CommentItemModel>()
    //音频播放时进度
    val progress = MutableLiveData<String>()
    // 动画
    val animPlay = MutableLiveData<Boolean>()
    //连载菜单
    val isMenu = MutableLiveData<Boolean>()
    //是否显示作者信息（包括菜单）
    val showAuthor = MutableLiveData<Boolean>()

    //问答
    val isQt = MutableLiveData<Boolean>()
    //问答用到的数据
    val askerName = MutableLiveData<String>()
    val answererName = MutableLiveData<String>()


    // 最后一个评论id
    protected var lastCommentId = "0"
    //文章id
    protected var contentId = "0"
    // 评论类型,进行加载评论时用
    protected var commentType = ""

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


   open fun getDetailsContent(category: String?, itemId: String) {
        showAuthor.set(true)
        when (category) {
            "0" -> {

            }
            else -> {
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
                        commentType = CateApi.getCateEn(category)
                        cId?.let {
                            contentId = it
                            lastCommentId = "0"
                            getComment(it, lastCommentId)
                        }
                    }
                }
            }
        }

    }


    fun getComment() {
        KLog.e("getComment.... next....   $contentId")
        getComment(contentId, lastCommentId)
    }


    protected fun getComment(contentId: String, commentId: String) {
        doTask {
            val model = executiveRequest {
                rep.getComment(commentType, contentId, commentId)
            }
            model?.let {
                commentList.updateData(it.data.data, commentId == "0")
                lastCommentId = it.data.data.last().id
            }
        }
    }


     fun onDestroy(){
        commentList.onCleared()
        VoicePlayerManager.vPlayer.onStopCallBack = null
    }

    override fun onCleared() {
        super.onCleared()
        KLog.e("AAA","..... 回收了。。。onCleared")
    }



}