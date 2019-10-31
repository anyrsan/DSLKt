package com.any.org.eanewsmudle.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.any.org.ankolibrary.async
import com.any.org.commonlibrary.log.KLog
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.model.bean.ThsItemModel
import com.any.org.eanewsmudle.model.repository.NewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/25 19.46
 * @details
 */
class ThsViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    val mList = AdapterDataObserver<ThsItemModel>()

    val empty = ObservableBoolean(false)

    private var page: Int = 1

    fun getList(isRefresh: Boolean = true): Observable<List<ThsItemModel>> = kotlin.run {
        if (isRefresh) page = 1
        newsRepository.getThs(page).async().doOnNext {
            it?.let {data->
                //刷新数据
                if (isRefresh) {
                    //检查是否为空
                    empty.set(data.isNullOrEmpty())
                }else{
                    page++
                }
                mList.updateData(data, isRefresh)
            }?: if(isRefresh) empty.set(true)
        }
    }


    override fun onCleared() {
        super.onCleared()
        mList.onCleared()
        KLog.e("viewmodel 失效")
    }

}