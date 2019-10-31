package com.any.org.eanewsmudle.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.any.org.commonlibrary.ui.BaseFragment
import com.any.org.eanewsmudle.ui.SinaFragment
import com.any.org.eanewsmudle.ui.ThsFragment
import com.any.org.eanewsmudle.ui.YLFragment

/**
 *
 * @author any
 * @time 2019/10/21 15.52
 * @details
 */
class NewsFragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList by lazy {
        mutableListOf<BaseFragment>(
            ThsFragment(),
            SinaFragment(),
            YLFragment()
        )
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int = fragmentList.size


}