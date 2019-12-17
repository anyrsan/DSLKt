package com.any.org.onemodule.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.nui.addFragment
import com.any.org.commonlibrary.nui.removeFragment
import com.any.org.onemodule.R
import kotlinx.android.synthetic.main.test_activity.*

/**
 *
 * @author any
 * @time 2019/12/16 11.57
 * @details
 */
class TestActivity : AppCompatActivity() {

    private var isAdd = false

    // error , memory leaking
//    private val testFg by lazy { DetailsContentFragment() }


    private val fragmentTag ="test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        testTv.viewOnClick {
            if (isAdd) {
                removeFragment(fragmentTag)
            } else {
                addFragment(OneDateFragment(), R.id.mainFL,fragmentTag)
            }
            isAdd = !isAdd
        }
    }
}