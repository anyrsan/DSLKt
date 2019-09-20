package com.any.org.eventbuslibrary.ktanno

/**
 *
 * @author any
 * @time 2019/07/30 17.22
 * @details
 */
annotation class Subscribe(
    val priority: Int = 0,
    val threadModel: ThreadModel = ThreadModel.CURRENT
)