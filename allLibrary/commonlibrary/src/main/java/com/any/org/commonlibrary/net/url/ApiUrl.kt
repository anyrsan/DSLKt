package com.any.org.commonlibrary.net.url

import com.any.org.commonlibrary.BuildConfig

/**
 *
 * @author any
 * @time 2019/9/20 11.39
 * @details 基础地址库
 */

//设置基础地址
const val BASE_URL = BuildConfig.BASE_URL

//配置login_url
const val LOGIN_URL = "$BASE_URL/1e5j6x"

//全路径 网址
const val LIST_URL = "https://api.apiopen.top/getJoke?page=1&count=2&type=video"