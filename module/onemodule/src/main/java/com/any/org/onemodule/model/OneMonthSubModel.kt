package com.any.org.onemodule.model

import com.any.org.commonlibrary.model.SectionModel
import com.any.org.onemodule.extend.getMonthFromDate

/**
 *
 * @author any
 * @time 2019/11/26 16.40
 * @details
 */
class OneMonthSubModel(val id: String, val date: String, val cover: String, val type: Int = 0):SectionModel(){
    override fun sectionTitle(): String {
        return  getMonthFromDate(date)
    }

}