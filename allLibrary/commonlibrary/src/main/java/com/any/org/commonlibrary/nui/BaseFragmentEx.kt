package com.any.org.commonlibrary.nui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 * @author any
 * @time 2019/9/18 16.44
 * @details fragment基类
 */
abstract class BaseFragmentEx : Fragment() {

    abstract fun getResourceId(): Int

    abstract fun initData()
    //懒加载数据
    abstract fun lazyData()

    abstract fun initEvent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getResourceId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        delayLoad(300, ::lazyData)
        initEvent()
    }

}



