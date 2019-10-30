package com.any.org.eanewsmudle.model.bean



/**
 * @author any
 * @time 2019/10/8 17.23
 * @details  数据对象
 */
class NewsModel : NewsBaseModel<NewsModel.NewsResultBean>() {

    class NewsResultBean {

        var status: NewsStatusBean? = null

        var timestamp: String? = null

        var data: NewsDataBean? = null

    }

}


class NewsDataBean(val feed:NewsFeedListBean?)

class NewsFeedListBean(val list:List<NewsItemModel>?)

class NewsStatusBean(val code: Int, val msg: String?)