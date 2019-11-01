package com.any.org.eanewsmudle.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.eanewsmudle.ui.SnFragment
import com.any.org.eanewsmudle.ui.ThsFragment
import com.any.org.eanewsmudle.ui.YlFragment

/**
 *
 * @author any
 * @time 2019/10/21 15.52
 * @details
 */
class NewsFragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mTitle by lazy { arrayOf("娱乐新闻","新浪新闻","同花顺") }

    private val fragmentList by lazy {
        mutableListOf(
            createFragment(fm, YlFragment::class.java.name),
            createFragment(fm, SnFragment::class.java.name),
            createFragment(fm, ThsFragment::class.java.name)
        )
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }


}