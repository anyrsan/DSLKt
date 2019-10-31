package com.any.org.eanewsmudle.model.repository

import com.any.org.eanewsmudle.model.local.NewsLocalProvider
import com.any.org.eanewsmudle.model.remote.ANwNetProvider

/**
 *
 * @author any
 * @time 2019/10/28 10.48
 * @details
 */
class ANewsRepository(
    private val netProvider: ANwNetProvider,
    private val localProvider: NewsLocalProvider
) {

    fun sinaList(map:Map<String,String>) = netProvider.sinaList(map)

    fun thsList(map:Map<String,String>) = netProvider.thsList(map)

    fun ylList(map:Map<String,String>) = netProvider.ylList(map)

}