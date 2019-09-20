package com.any.routerannotation

/**
 *
 * @author any
 * @time 2019/07/10 10.20
 * @details
 */
@Target(AnnotationTarget.CLASS)
annotation class KInterceptor(val priority:Int=5)