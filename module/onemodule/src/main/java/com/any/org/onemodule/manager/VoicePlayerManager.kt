package com.any.org.onemodule.manager

import android.media.MediaPlayer
import com.any.org.ankolibrary.doAsyncTask
import com.any.org.commonlibrary.log.KLog
import java.lang.Exception
import kotlin.math.roundToInt

/**
 *
 * @author any
 * @time 2019/8/13 19.31
 * @details 播放有声读物
 */
class VoicePlayerManager private constructor() {


    private var mediaPlayer: MediaPlayer? = null

    //申请
    companion object {
        val vPlayer by lazy { VoicePlayerManager() }
    }


    private fun initMedia() {
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer()
    }

    //是否在播放中
    fun isPlayIng(currPlayerUrl: String): Boolean {
        val playing = mediaPlayer?.isPlaying ?: false
        val sameUrl = playerUrl == currPlayerUrl
        KLog.e("aabb", "$playing   $sameUrl")
        return playing && sameUrl
    }

    //播放URl
    private var playerUrl: String? = null

    @Volatile
    private var canPlayer = false   // 这个只是代表当前 播放线程中执行的事件是否走完一个流程

    fun startPlay(
        path: String?,
        onPlayState: (playStart: Boolean, isError: Boolean, isCompletion: Boolean) -> Unit,
        onPlayProgress:(progress:String)->Unit
    ) {
        //
        initMedia()
        KLog.e("aabb... 播放地址:  $path   $mediaPlayer")
        mediaPlayer?.apply {
            KLog.e("aabb... start....   $canPlayer")
            //错误了
            if (path.isNullOrEmpty()) {
                onPlayState.invoke(false, true, false)
                return@apply
            }
            //不处理
            if (canPlayer) {
                onPlayState.invoke(false, true, false)
                return@apply
            }
            //停止播放
            if (isPlaying) {
                stop()
                KLog.e("aaa", "<<<<stop...$path")
            }
            //增加临时记录
            playerUrl = path
            //
            doAsyncTask {
                canPlayer = true
                try {
                    KLog.e("msg", "<<<>>><<<player....>>>><<<>>")
                    reset()
                    setDataSource(path)
                    prepare()
                    start()
                    onPlayState.invoke(true, true, false)
                    //播放进度
                    setOnErrorListener { mp, _, _ ->
                        KLog.e("aaa", "<<<<<<>>>>>error<<<<>>>>>>.... $path")
                        onPlayState.invoke(false, true, false)
                        true
                    }
                    setOnCompletionListener {
                        KLog.e("aaa", ">>>>>>>>over.... $path")
                        onPlayState.invoke(false, false, true)
                    }
                    //获取进度
                    updateProgress(onPlayProgress)
                    KLog.e("aaa", "########startPlay.... $path")
                } catch (e: Exception) {
                    KLog.e("aaa", "++++++++error.... $path")
                    e.printStackTrace()
                    onPlayState.invoke(false, true, false)
                }
                canPlayer = false
            }


        }
    }

    //更新进度
  private  fun updateProgress(callBack: (msg: String) -> Unit) {
        doAsyncTask {
            mediaPlayer?.apply {
                while (isPlaying) {   //如果正在播放，则要进度更新
                    KLog.e("msg", "... 当前播放进度 $currentPosition   总进度$duration")
                    val value = duration - currentPosition
                    val progressMsg = formatTime(value.toLong())
                    callBack.invoke(progressMsg)
                    if (currentPosition == duration) return@doAsyncTask
                    Thread.sleep(1000)
                }
                KLog.e("msg", "... 结束了： $currentPosition   总进度$duration")
            }
            KLog.e("msg", "...")
        }
    }

    //删除播放
    fun deletePlayer(currPlayerUrl: String?) {
        currPlayerUrl?.let {
            if (isPlayIng(it)) {
                try {
                    mediaPlayer?.apply {
                        KLog.e("aabb", "stop play...  $isPlaying")
                        if (isPlaying) {
                            stop()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    //停止回调
    var onStopCallBack: (() -> Unit)? = null

    fun stopPlay() {
        try {
            mediaPlayer?.apply {
                KLog.e("aabb", "stop play...  $isPlaying")
                if (isPlaying) {
                    stop()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //停止吧
        onStopCallBack?.invoke()
    }


    fun onDestroy() {
        mediaPlayer?.apply {
            stopPlay()
            release()
        }
        mediaPlayer = null
    }


    private fun formatTime(duration: Long): String {
        val msg = (duration / 1000.0).roundToInt()
        val mm = msg / 60
        val ss = msg % 60
        val mt = if (mm >= 10) mm.toString() else "0$mm"
        val st = if (ss >= 10) ss.toString() else "0$ss"
        return "$mt:$st"
    }


}