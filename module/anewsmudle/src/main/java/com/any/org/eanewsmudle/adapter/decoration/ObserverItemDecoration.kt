package com.any.org.eanewsmudle.adapter.decoration

import android.content.Context
import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.widget.SectionItemDecoration
import com.any.org.eanewsmudle.adapter.obser.AdObserver
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver

/**
 *
 * @author any
 * @time 2019/10/30 16.21
 * @details  横线切屏
 */
class ObserverItemDecoration<M : SectionModel>(context: Context, list: AdapterDataObserver<M>) :
    SectionItemDecoration(context, null) {

    init {
        list.addListener(object : AdObserver<M> {
            override fun updateData(t: List<M>?, new: Boolean) {
                if (new) {
                    setData(t)
                } else {
                    t?.let {
                        addData(it)
                    }
                }
            }

        })
    }


}