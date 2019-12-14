package com.any.org.onemodule.data.remote

import com.any.org.onemodule.model.CommentModel
import com.any.org.onemodule.model.EssayModel
import com.any.org.onemodule.model.SerialContentModel
import com.any.org.onemodule.net.ARTICLE_DETAIL_URL
import com.any.org.onemodule.net.COMMENT_DATA_URL
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author any
 * @time 2019/11/29 18.48
 * @details 协程api
 */
interface DetailsApi {

    @GET(ARTICLE_DETAIL_URL)
    suspend fun getEssayDetail(@Path("type") cateEnType: String, @Path("itemId") itemId: String): EssayModel

    @GET(ARTICLE_DETAIL_URL)
    suspend fun getSerialDetail(@Path("type") cateEnType: String, @Path("itemId") itemId: String): SerialContentModel

    @GET(COMMENT_DATA_URL)
    suspend fun getComment(
        @Path("type") cateEnType: String, @Path("contentId") contentId: String, @Path(
            "commentId"
        ) commentId: String
    ): CommentModel
}