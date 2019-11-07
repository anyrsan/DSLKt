package com.any.org.rxviewmodel.model.repository

import com.any.org.rxviewmodel.model.local.LocalDataProvider

/**
 *
 * @author any
 * @time 2019/11/6 19.48
 * @details
 */
class NewRepository(private val lp: LocalDataProvider) {


    fun getData() = lp.testData()


    fun test() = lp.test()
}