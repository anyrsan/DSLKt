package com.any.routercompliecore

/**
 *
 * @author any
 * @time 2019/07/10 15.00
 * @details
 */
interface InterceptorCallback {

    /**
     * Continue process
     *
     * @param postcard route meta
     */
    fun onContinue(routerConfig: RouterConfig)

    /**
     * Interrupt process, pipeline will be destroy when this method called.
     *
     * @param exception Reson of interrupt.
     */
    fun onInterrupt(exception: Throwable?)
}