package com.any.org.eanewsmudle

import com.any.org.ankolibrary.bindLifecycle
import com.any.org.ankolibrary.subscription
import com.any.org.commonlibrary.log.KLog
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // android test
//    fun testClose(){
//        KLog.e("3S开始请求数据")
//        delayedPost(1000) {  // test success
//            KLog.e("执行数据请求")
//            newsViewModel.getList(null).bindLifecycle(this).subscription()
//            //关闭界面
//            delayedPost(100) {
//                finish()
//                KLog.e("关闭了界面，是否自动取消关联")
//            }
//        }
//    }
}
