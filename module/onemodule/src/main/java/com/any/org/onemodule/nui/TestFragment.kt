package com.any.org.onemodule.nui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.removeFragment
import com.any.org.onemodule.R
import com.any.org.onemodule.nviewmodel.TestViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2020/1/11 14.39
 * @details
 */
class TestFragment : Fragment() {

    private val vm by viewModel<TestViewModel>()

//    private val vm by sharedViewModel<TestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        KLog.e("onCreateView")
        return inflater.inflate(R.layout.test_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KLog.e("onViewCreated   $view")
        //处理事件
        view.findViewById<View>(R.id.taskOneBt).viewOnClick {
            vm.doOneTask()
        }

        //移除
        view.findViewById<View>(R.id.removeBt).viewOnClick {
            val tf =requireActivity() as TestCoroutineActivity
            tf.removeFragment("test")
        }
    }

}