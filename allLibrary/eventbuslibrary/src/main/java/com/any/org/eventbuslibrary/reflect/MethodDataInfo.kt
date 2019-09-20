package com.any.org.eventbuslibrary.reflect

import com.any.org.eventbuslibrary.ktanno.ThreadModel
import java.lang.reflect.Method

/**
 *
 * @author any
 * @time 2019/07/30 17.44
 * @details
 */
class MethodDataInfo(
    val method: Method,
    val argType: Class<*>,
    val priority: Int,
    val threadModel: ThreadModel
)