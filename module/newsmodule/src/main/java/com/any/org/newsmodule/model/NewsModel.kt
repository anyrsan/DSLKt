package com.any.org.newsmodule.model

import com.any.org.dslnetlibrary.HttpBaseModel

/**
 *
 * @author any
 * @time 2019/10/8 17.23
 * @details
 */
class NewsModel : HttpBaseModel() {

    var result: List<NewsItemModel>? = null

    class NewsItemModel {
        var sid: String? = null
        var text: String? = null
        var uid: String? = null
        var name: String? = null
        var header: String? = null
        var passtime: String? = null
    }

}