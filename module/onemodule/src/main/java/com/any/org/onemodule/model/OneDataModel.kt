package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/11 18.01
 * @details
 */
class OneDataModel {
    var res: Int = -1
    var data: OneDataSubModel? = null
}


class OneDataSubModel {
    var id: String? = null

    var weather: OneDataWeatherModel? = null

    var date: String? = null

    var content_list: List<OneDataItemModel>? = null


    var menu: OneDataMenuModel? = null

}


class OneDataMenuModel {

    var vol: String? = null

    var list: List<OneDataMenuItemModel>? = null

}