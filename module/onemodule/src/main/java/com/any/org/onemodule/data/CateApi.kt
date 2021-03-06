package com.any.org.onemodule.data

/**
 *
 * @author any
 * @time 2019/11/11 17.01
 * @details
 */

object CateApi {

    /**
     *  {
    0: "图文",
    1: "阅读",
    2: "连载",
    3: "问答",
    4: "音乐",
    5: "影视",
    8: "电台",
    0: "hp",
    1: "essay",
    2: "serialcontent",
    3: "question",
    4: "music",
    5: "movie",
    8: "radio",
    };
     */
    private fun cateFullData(en: Boolean = true) = lazy {
        val map = hashMapOf<Int, String>()
        map[0] = if (en) "hp" else "图文"
        map[1] = if (en) "essay" else "阅读"
        map[2] = if (en) "serialcontent" else "连载"
        map[3] = if (en) "question" else "问答"
        map[4] = if (en) "music" else "音乐"
        map[5] = if (en) "movie" else "影视"
        map[8] = if (en) "radio" else "电台"
        map
    }

    private val cateMenuZH by cateFullData(false)

    private val cateMenuEN by cateFullData()


    fun getCateEn(cateType: String?): String {
        return try {
            val key = cateType?.toInt() ?: 0
            return "${cateMenuEN[key]}"
        } catch (e: Exception) {
            e.printStackTrace()
            "hp"
        }
    }

    @JvmStatic
    @JvmOverloads
    fun getCateTitle(cateType: String?, isLine: Boolean = true): String {
        return try {
            val key = cateType?.toInt() ?: 0
            return if (isLine) "- ${cateMenuZH[key]} -" else "${cateMenuZH[key]}"
        } catch (e: Exception) {
            e.printStackTrace()
            if (isLine) "- 未知 -" else "未知"
        }
    }


    fun getType(cateType: String?): Int {
        return cateType?.toInt() ?: -1
    }


}




