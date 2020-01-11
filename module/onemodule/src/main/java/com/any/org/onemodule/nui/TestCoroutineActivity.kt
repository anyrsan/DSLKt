package com.any.org.onemodule.nui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.nui.addFragment
import com.any.org.onemodule.R
import com.any.org.onemodule.nviewmodel.TestViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 *
 * @author any
 * @time 2020/1/11 14.20
 * @details 测试协程，是否存在内存问题
 */
class TestCoroutineActivity : AppCompatActivity() {

    private val vm by viewModel<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        addFragment(TestFragment(), R.id.mainLl, "test")

        findViewById<View>(R.id.testTv).viewOnClick {
            taskOneBt()
        }
    }

    //启动协程做一个无限循环任务
    fun taskOneBt() {
        vm.doOneTask()
    }

}