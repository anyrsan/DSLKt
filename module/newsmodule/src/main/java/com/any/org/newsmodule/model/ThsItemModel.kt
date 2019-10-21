package com.any.org.newsmodule.model

import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.utils.DateUtils
import com.google.gson.annotations.SerializedName

/**
 * @author any
 * @time 2019/10/21 14.54
 * @details  这种数据的格式 返回很特殊 ，不推荐
 */
class ThsItemModel : SectionModel()  {

    /**
     * seq : 614510926
     * guid : 55267ec6dfda4d3fb06fa113e5a9c2d8
     * nosumm : 0
     * mkcontent : 1
     * url : http://stock.10jqka.com.cn/20191021/c614510926.shtml
     * xmlsource : http://stock.10jqka.com.cn/20191021/c614510926.xml
     * title : 石油矿业开采板块拉升
     * ctime : 1571640391
     * bq : 1
     * obq : 1
     * source : 同花顺金融研究中心
     * author : null
     * content : 石油矿业开采板块持续拉升，金岭矿业涨超7%，海南矿业、宏达矿业、粤桂股份等跟涨。
     * rtime : 1571640391
     * belongdomain : stock
     * artpic : null
     * stocks : 1A0001
     * isvalid : 1
     * istop : []
     * sourceurl : null
     * codenature : null
     * nature :
     * import :
     * class : 004021,002002011,080005019002
     * append : {"fromNewFast":1,"jiepanstyle":"1","jiepantag":"{\"21111\":\"\\u5f02\\u52a8\"}","inputFrom":"CMP","userId":"1150349","muserName":"侯晓腾","uname":"aHh0","modifierid":"1150349","modifiername":"侯晓腾","pushType":"normal","usCodes":[],"hkcode":[],"copyright":1,"pic":[]}
     * taginfo : ["50012957_\u91c7\u77ff\u4e1a_0.993_0","50024426_\u77f3\u6cb9_0.993_1","50001205_\u77f3\u6cb9\u5f00\u91c7_0.993_0"]
     */

    var seq: Int = 0
    var guid: String? = null
    var nosumm: Int = 0
    var mkcontent: Int = 0
    var url: String? = null
    var xmlsource: String? = null
    var title: String? = null

    var ctime: Long = 0
        get() = field * 1000     // 注意下

    var bq: Int = 0
    var obq: String? = null
    var source: String? = null
    var author: Any? = null
    var content: String? = null
    var rtime: Int = 0
    var belongdomain: String? = null
    var artpic: Any? = null
    var stocks: String? = null
    var isvalid: String? = null
    var istop: String? = null
    var sourceurl: Any? = null
    var codenature: Any? = null
    var nature: String? = null
    @SerializedName("import")
    var importX: String? = null
    @SerializedName("class")
    var classX: String? = null
    var append: AppendBean? = null
    var taginfo: String? = null

    override fun sectionTitle(): String {
        return DateUtils.formatDateNew(ctime,showWeek = false)
    }


    class AppendBean {
        /**
         * fromNewFast : 1
         * jiepanstyle : 1
         * jiepantag : {"21111":"\u5f02\u52a8"}
         * inputFrom : CMP
         * userId : 1150349
         * muserName : 侯晓腾
         * uname : aHh0
         * modifierid : 1150349
         * modifiername : 侯晓腾
         * pushType : normal
         * usCodes : []
         * hkcode : []
         * copyright : 1
         * pic : []
         */


        var fromNewFast: Int = 0
        var jiepanstyle: String? = null
        var jiepantag: String? = null
        var inputFrom: String? = null
        var userId: String? = null
        var muserName: String? = null
        var uname: String? = null
        var modifierid: String? = null
        var modifiername: String? = null
        var pushType: String? = null
        var copyright: Int = 0
        var usCodes: List<*>? = null
        var hkcode: List<*>? = null
        var pic: List<*>? = null



    }
}
