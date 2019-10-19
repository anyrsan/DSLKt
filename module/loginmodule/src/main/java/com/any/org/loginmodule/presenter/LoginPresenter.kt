package com.any.org.loginmodule.presenter

import android.content.Context
import com.any.org.commonlibrary.CustomToast
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.dslnetlibrary.http
import com.any.org.loginmodule.model.UserModel
import com.any.org.loginmodule.service.LoginServiceApi
import com.trello.rxlifecycle3.LifecycleProvider

/**
 *
 * @author any
 * @time 2019/9/20 13.50
 * @details
 */
class LoginPresenter (private val mContext: Context,private  val lProvider: LifecycleProvider<*>){

    private val serviceApi by lazy { LoginServiceApi.getServiceApi() }

    fun doLogin(
        userName: String,
        pwd: String,
        onResult: (it: HttpBaseModel?, error: Boolean) -> Unit
    ) {
        val paramsMap = mutableMapOf<String, String>("user" to userName,"pwd" to pwd)
//        val requestData = serviceApi.doLogin(paramsMap)
        val requestData = serviceApi.testLogin()
        http<UserModel> {
            data = requestData
            context = mContext
            lifecycleProvider = lProvider
            successCallBack {
                onResult.invoke(it, false)
            }
            errorCallBack { errorCode, errorMessage ->
                onResult.invoke(null, true)
                CustomToast.showNetMsg(mContext, errorMessage)
            }

        }


    }


}