package com.any.org.eanewsmudle.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.any.org.ankolibrary.async
import com.any.org.commonlibrary.log.KLog
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.model.repository.NewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/25 19.46
 * @details
 */
class YLViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    val mList = AdapterDataObserver<YLNewsModel.DataBean>()

    val empty = ObservableBoolean(false)

    val isLoading = ObservableBoolean(true)

    private var cTime: Long? = null

    @JvmOverloads
    fun getYLNews(isRefresh: Boolean = true): Observable<YLNewsModel> = kotlin.run {
        if (isRefresh) {
            cTime = null
            isLoading.set(true)
        }
        newsRepository.getYLNews(cTime).async(500).doOnNext {
            //刷新数据
            if (isRefresh) {
                //检查是否为空
                empty.set(it.data.isNullOrEmpty())
                //关闭刷新
                isLoading.set(false)
            }
            cTime = it.req_time
            it?.data.let { data ->
                mList.updateData(data, isRefresh)
            }

            KLog.e("ooommm  load=${isLoading.get()}")
        }.doOnError {
            if (isRefresh) {
                isLoading.set(false)
                empty.set(true)
            }
            mList.updateData(null, isRefresh)
            KLog.e("出错了，哈哈")
        }
    }


    override fun onCleared() {
        super.onCleared()
        mList.onCleared()
        KLog.e("viewmodel 失效")
    }

}