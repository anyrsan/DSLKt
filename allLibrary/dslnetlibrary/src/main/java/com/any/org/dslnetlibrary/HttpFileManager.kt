
package com.any.org.dslnetlibrary

import android.text.TextUtils
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.RandomAccessFile


fun httpFile(block: HttpFileManagerBuild.() -> Unit) {
    val httpFileManager = HttpFileManagerBuild().apply(block).build()
    httpFileManager.doTask()
}

class HttpFileManagerBuild {
    var call: Call<ResponseBody>? = null
    var filePath: String? = null
    private var downProgress: ((progress: Int, isDone: Boolean) -> Unit)? = null
    private var callBack: ((isDone: Boolean) -> Unit)? = null
    private var errorCallBack: (((errorCode: Int, errorMessage: String?) -> Unit)?) = null

    fun errorCallBack(errorCallBack: (((errorCode: Int, errorMessage: String?) -> Unit)?)) {
        this.errorCallBack = errorCallBack
    }

    fun callBack(callBack: ((isDone: Boolean) -> Unit)) {
        this.callBack = callBack
    }

    fun downProgress(downProgress: ((progress: Int, isDone: Boolean) -> Unit)) {
        this.downProgress = downProgress
    }

    fun build(): HttpFileManager = HttpFileManager(call, filePath, downProgress, callBack, errorCallBack)
}


/**
 *
 * @author any
 * @time 2019/06/29 10.25
 * @details 文件下载流，支持断点下载
 */
class HttpFileManager(
    private val call: Call<ResponseBody>?,
    private val filePath: String?,
    private val downProgress: ((progress: Int, isDone: Boolean) -> Unit)?,
    private val callBack: ((isDone: Boolean) -> Unit)?,
    private val errorCallBack: (((errorCode: Int, errorMessage: String?) -> Unit)?)
) {


    fun doTask() {
        //处理下载逻辑
        Observable.create<Boolean> {
            Log.e("msg", ".....开始发送请求。。。${Thread.currentThread().name}")
            val response = call?.execute()
            Log.e("msg", ".....获取response对象 。。。$response")
            val result = writeResponseBodyToFile(response, filePath, downProgress = downProgress)
            it.onNext(result)
            it.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe({
            Log.e("msg", ".....下载结果。。。$it")
            callBack?.invoke(it)
        }, {
            //下载出错，返回错误原因
            callBack?.invoke(false)
            val responeThrowable = ExceptionHandle.handleException(it, null)
            errorCallBack?.invoke(responeThrowable.code, responeThrowable.msg)
        })


    }


    private fun writeResponseBodyToFile(
        response: Response<ResponseBody>?,
        filePath: String?,
        isCanResume: Boolean = false,
        downProgress: ((progress: Int, isDone: Boolean) -> Unit)?
    ): Boolean {
        val file = File(filePath)
        return try {
            //创建文件
            createFile(filePath)
            val byteBuffer = ByteArray(1024)
            // 文件大小
            var fileSize = response?.body()?.contentLength() ?: -1
            //说明文件下载完成了
            if (fileSize == 0L) {
                downProgress?.invoke(100, true)
                return true
            }
            Log.e("msg", "fileSize...$fileSize")
            //输入流
            val inputStream = response?.body()?.byteStream()
            //输出流
            val raf = RandomAccessFile(file, "rw")
            var downSize = 0L
            // 续点下载
            if (isCanResume) {
                downSize = raf.length()
                raf.seek(downSize)
                //加上以前的大小
                fileSize += downSize
            } else {
                raf.seek(0)
            }

            var tempProgress = -1
            // 处理写入
            inputStream?.let {
                while (true) {
                    val read = it.read(byteBuffer)
                    if (read == -1) {
                        break
                    }
                    //输出到文件
                    raf.write(byteBuffer, 0, read)
                    //进度条
                    downSize += read
                    //回调进度
                    //进度百分比
                    val percent = (downSize.toFloat() / fileSize * 100).toInt()
                    if (tempProgress != percent) {
                        downProgress?.invoke(percent, downSize == fileSize)
                        tempProgress = percent
                    }
                }
            }
            raf.close()
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            file.deleteRecursively()
            Log.e("msg", "down...FileNotFoundException..." + e.localizedMessage + "===>" + e.message)
            false
        } catch (e: Exception) {
            //其它异常暂时不删除文件
//            file.deleteRecursively()
            Log.e("msg", "down...Exception..." + e.localizedMessage + "===>" + e.message)
            throw e
        }
    }


    private fun createFile(filePath: String?) {
        if (TextUtils.isEmpty(filePath))
            throw FileNotFoundException()
        val file = File(filePath)
        if (file.exists()) {
            return
        }
        val parenFile = file.parentFile
        if (parenFile != null) {
            val bool = parenFile.mkdirs()
            Log.e("msg", "boolean: $bool")
        }

    }

}