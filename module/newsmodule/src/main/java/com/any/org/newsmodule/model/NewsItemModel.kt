package com.any.org.newsmodule.model

import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.utils.DateUtils

/**
 * @author any
 * @time 2019/10/19 15.26
 * @details  其中一个Item  SiNa
 */
class NewsItemModel : SectionModel() {


    /**
     * id : 1438182
     * zhibo_id : 152
     * type : 0
     * rich_text : 【科创板上市19天后  传音控股计划将25亿元闲置募资用于理财】记者获悉，传音控股计划使用不超过人民币25亿元的部分暂时闲置募集资金进行现金管理，购买投资产品。传音控股于9月30日在科创板上市，募集资金28.12亿元。
     * multimedia :
     * commentid : live:finance-152-1438182:0
     * compere_id : 0
     * creator : weiyu3@staff.sina.com.cn
     * mender : weiyu3@staff.sina.com.cn
     * create_time : 2019-10-19 08:34:51
     * update_time : 2019-10-19 08:36:56
     * is_need_check : 0
     * check_time : 1970-01-01 08:00:01
     * check_status : 1
     * check_user :
     * is_delete : 0
     * top_value : 0
     * is_focus : 0
     * source_content_id : 0
     * anchor_image_url :
     * anchor : 直播员
     * ext : {"stocks":[{"market":"cn","symbol":"sh688036","key":"\u4f20\u97f3\u63a7\u80a1"}],"needPush":false,"needAIPush":false,"needCMSLink":true,"needCalender":false,"docurl":"https:\/\/finance.sina.com.cn\/7x24\/2019-10-19\/doc-iicezuev3218731.shtml","docid":"icezuev3218731"}
     * old_live_cid : 0
     * tag : [{"id":"3","name":"公司"},{"id":"10","name":"A股"}]
     * like_nums : 0
     * comment_list : {"list":[{"comment_imgs":"","code":"4001","show_loc":"1","parent_mid":"0","news_mid_source":"0","layer":"1","rank":"3","mid":"5DAA6803-DF68D26B-16AD4DB39-816-91A","parent_nick":"","video":"","vote":"0","uid":"6087301945","area":"上海","channel_source":"","content":"典型的人治市场","nick":"用户6087301945","hot":"0","status_uid":"0","content_ext":{"reply":0,"weibourl":""},"ip":"223.104.210.107","media_type":"0","config":"wb_verified=0&wb_screen_name=用户6087301945&area=上海&wb_profile_img=http://n.sinaimg.cn/sinanews/eb9bf575/20190108/TouXiang120.png&followers_count=31&wb_user_id=6087301945&wb_time=1571448835&layer=1&show_loc=1","channel":"live","comment_mid":"0","status":"M_PASS","openid":"","newsid_source":"","parent":"","status_show":"1","status_cmnt_mid":"","is_top":"0","parent_profile_img":"","news_mid":"0","import_type":"","newsid":"finance-152-1438182","level":"4","count_layer":"0","parent_uid":"0","parent_new":"","thread_mid":"0","thread":"","status2":"M_PASS","did":"","is_agree":"0","top_desc":"","against":"1571448835","check_type":"2","usertype":"financeapp","length":"7","profile_img":"http://n.sinaimg.cn/sinanews/eb9bf575/20190108/TouXiang120.png","batch_type":"","time":"2019-10-19 09:33:55","login_type":"0","is_hot":"0","audio":"","agree":"3"},{"comment_imgs":"","code":"4001","show_loc":"1","parent_mid":"0","news_mid_source":"0","layer":"1","rank":"5","mid":"5DAA6076-AB2CA1A9-1A85E5954-816-9A4","parent_nick":"","video":"","vote":"0","uid":"7119722836","area":"湖北宜昌","channel_source":"","content":"不差钱的公司跑来上市，上市以后圈了钱去买理财，解禁期到了就卖股套现！","nick":"长不高的韭菜","hot":"0","status_uid":"0","content_ext":{"reply":0,"weibourl":""},"ip":"171.44.161.169","media_type":"0","config":"wb_verified=0&wb_screen_name=长不高的韭菜&area=湖北宜昌&wb_profile_img=https://tvax2.sinaimg.cn/crop.0.0.1006.1006.50/007LPD9ily8g2qud39z40j30ry0rygmp.jpg&followers_count=3&wb_user_id=7119722836&wb_time=1571446902&layer=1&show_loc=1","channel":"live","comment_mid":"0","status":"M_PASS","openid":"","newsid_source":"","parent":"","status_show":"1","status_cmnt_mid":"","is_top":"0","parent_profile_img":"","news_mid":"0","import_type":"","newsid":"finance-152-1438182","level":"3","count_layer":"0","parent_uid":"0","parent_new":"","thread_mid":"0","thread":"","status2":"M_PASS","did":"","is_agree":"0","top_desc":"","against":"1571446902","check_type":"2","usertype":"financeapp","length":"34","profile_img":"https://tvax2.sinaimg.cn/crop.0.0.1006.1006.50/007LPD9ily8g2qud39z40j30ry0rygmp.jpg","batch_type":"","time":"2019-10-19 09:01:42","login_type":"0","is_hot":"0","audio":"","agree":"5"}],"total":20}
     * compere_info :
     */

    var id: Int = 0
    var rich_text: String? = null
    var create_time: String? = null

    override fun sectionTitle(): String {
        return create_time?.let {
            DateUtils.formatDateNew(it,showWeek = false)
        } ?: "error"
    }

}
