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
const val LOGIN_URL = "${BASE_URL}1e5j6x"

//全路径 网址
const val LIST_URL = "https://api.apiopen.top/getJoke?page=1&count=2&type=video"

//sina url
const val SINA_NEWS="http://zhibo.sina.com.cn/api/zhibo/feed"
// ths
const val THS_NEWS ="http://comment.10jqka.com.cn/api/realtime.php"

//sina mix//
//https://cre.mix.sina.com.cn/api/v3/get?cateid=1Q&cre=tianyi&mod=pcent&merge=3&statics=1&length=15&up=0&down=0&tm=1572401371
const val SINA_YL="https://cre.mix.sina.com.cn/api/v3/get"