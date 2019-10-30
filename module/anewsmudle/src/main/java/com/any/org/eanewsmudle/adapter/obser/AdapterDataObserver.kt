package com.any.org.eanewsmudle.adapter.obser

/**
 *
 * @author any
 * @time 2019/10/30 14.59
 * @details
 */
class AdapterDataObserver<T> : AdObserver<T>{

    override fun updateData(t: List<T>?, news: Boolean) {
        observerListeners.forEach {
            it.updateData(t,news)
        }
    }

    private val observerListeners by lazy { mutableListOf<AdObserver<T>>() }

    fun addListener(adObserver: AdObserver<T>) =
        kotlin.run { if (!checkObserver(adObserver)) observerListeners.add(adObserver) }

    fun removeListener(adObserver: AdObserver<T>) =
        kotlin.run { if (checkObserver(adObserver)) observerListeners.remove(adObserver) }

    fun clearAllLister() = observerListeners.clear()

    private fun checkObserver(adObserver: AdObserver<T>): Boolean =
        observerListeners.contains(adObserver)


}