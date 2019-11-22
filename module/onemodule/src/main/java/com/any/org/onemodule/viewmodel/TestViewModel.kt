package com.any.org.onemodule.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.doAsyncResultTask
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.repository.OneRepository
import com.any.org.onemodule.data.room.ArticleDetailData
import com.any.org.onemodule.model.ArticleDetailModel
import com.any.org.onemodule.model.OneDataModel

/**
 *
 * @author any
 * @time 2019/11/16 14.27
 * @details
 */
class TestViewModel(private val rep: OneRepository) : BaseViewModel() {

    val oneData by lazy { MutableLiveData<OneDataModel>() }

    val articleData by lazy { MutableLiveData<ArticleDetailModel>() }

    fun loadOne(view: View) {
        launch {
            rep.getOneData().async().subscribe({
                oneData.set(it)
            }, {
                KLog.e("异常 $it")
            }, {})
        }
    }

    fun getArticle(view: View) {

        launch {
            // id
            val id = oneData.value?.data?.content_list?.get(0)?.id ?: "0"
            // cateId
            val cateId = oneData.value?.data?.content_list?.get(0)?.category?.toInt() ?: 0

            KLog.e("id=$id   cateId = $cateId")

//            doAsyncResultTask {
////                val num = rep.deleteAll()
////               KLog.e("删除数据  $num")
//
//               val result = rep.getArticle(id)
//
//               result.subscribe{it:ArticleDetailData,error:Throwable->
//
//                   KLog.e("实际  $it   $error")
//
//               }
//
//            }

            rep.getArticleModel(id, cateId).async().subscribe { t1, t2 ->

                articleData.set(t1)

                KLog.e("出错了？？？。。。 $t2")
            }



        }

    }


}