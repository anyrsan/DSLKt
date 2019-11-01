package com.any.org.eanewsmudle.model.repository

import com.any.org.eanewsmudle.model.local.ANewsLocalProvider
import com.any.org.eanewsmudle.model.remote.ARemoteProvider

/**
 *
 * @author any
 * @time 2019/10/28 10.48
 * @details    数据的来源，并不是viewmdoel所关注的， remoteProvider 与 localProvider 在这里实现数据的提供
 */
class ANewsRepository(
    private val remoteProvider: ARemoteProvider,
    private val localProvider: ANewsLocalProvider
) {

    fun sinaList(map: Map<String, String>) = remoteProvider.sinaList(map)

    fun thsList(map: Map<String, String>) = remoteProvider.thsList(map)

    fun ylList(map: Map<String, String>) = remoteProvider.ylList(map)

}