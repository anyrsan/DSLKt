package com.any.org.onemodule.data.local

import com.any.org.onemodule.data.room.ArticleDetailData
import com.any.org.onemodule.model.ArticleDetailDataModel

/**
 *
 * @author any
 * @time 2019/11/11 20.07
 * @details
 */
object ConvertData {


    fun convertToDataModel(dataModel: ArticleDetailDataModel?, atData: ArticleDetailData) {
        dataModel?.apply {
            auth_it = atData.authorIt
            content_id = atData.id
            hp_author = atData.author
            hp_author_introduce = atData.authorIntroduce
            hp_content = atData.content
            hp_makettime = atData.makettime
        }
    }

    fun convertToData(dataModel: ArticleDetailDataModel?): ArticleDetailData {
        return dataModel?.run {
            ArticleDetailData(
                content_id.ct(),
                hp_author.ct(),
                auth_it.ct(),
                hp_author_introduce.ct(),
                hp_content.ct(),
                hp_makettime.ct()
            )
        } ?: ArticleDetailData("", "", "", "", "", "")

    }

}


fun String?.ct(): String = this?.let { it } ?: ""