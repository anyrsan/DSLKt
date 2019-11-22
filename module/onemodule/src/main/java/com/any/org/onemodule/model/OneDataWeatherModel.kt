package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/16 14.08
 * @details
 */
class OneDataWeatherModel {

    var city_name: String? = null

    var date: String? = null

    var temperature: String? = null

    var humidity: String? = null

    var climate: String? = null

    var wind_direction: String? = null


    var hurricane: String? = null

    var icons: OneDataIconModel? = null


}

class OneDataIconModel {
    var day: String? = null
    var night: String? = null
}