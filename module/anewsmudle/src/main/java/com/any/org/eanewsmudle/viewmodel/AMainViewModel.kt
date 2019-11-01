package com.any.org.eanewsmudle.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author any
 * @time 2019/11/1 11.02
 * @details 无逻辑，只是定义一个变量，用于databinding 中view 引用值
 */
class AMainViewModel : ViewModel() {

    val mTitle = MutableLiveData<String>()

}