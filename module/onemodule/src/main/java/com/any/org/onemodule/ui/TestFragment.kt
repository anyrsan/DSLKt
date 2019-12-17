package com.any.org.onemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.any.org.onemodule.R
import leakcanary.ObjectWatcher

/**
 *
 * @author any
 * @time 2019/12/16 11.45
 * @details
 */
class TestFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }
}