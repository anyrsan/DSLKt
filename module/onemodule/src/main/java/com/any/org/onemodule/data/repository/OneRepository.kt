package com.any.org.onemodule.data.repository

import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetProvider
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.ArticleDetailModel
import io.reactivex.Single
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/11 18.03
 * @details
 */
class OneRepository(
    private val netProvider: NetProvider,
    private val localProvider: LocalProvider
) {


    fun getOneData(date: String , address: String ) =
        kotlin.run {
            netProvider.getOneData(date, address)
        }


    // 优先从数据库获取，然后再执行网络请求，然后再保存， 其实线上项目可以基于文章发布时间对比，是否存在更新
    fun getArticleModel(id: String, cateId: Int): Single<ArticleDetailModel> = kotlin.run {
//        val cateEn = CateApi.cateMenuEN[cateId]!!
//        Observable.concat(
//            localProvider.getOneArticleDetail(id),
//            netProvider.getOneArticleDetail(id, cateEn)
//        ).doAfterNext {
//            //保存数据
//            localProvider.saveArticleDetail(it)
//        }.onErrorResumeNext(ObservableSource<ArticleDetailModel> {
//
//            KLog.e("error... ")
//        }).doOnError {
//
//            KLog.e("出错了呀。。。 哈哈")
//        }




        localProvider.getOneArticleDetail(id)

    }


    fun deleteAll() = localProvider.deleteAll()

    fun getArticle(id:String) = localProvider.getArticle(id)


}