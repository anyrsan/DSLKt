package com.any.org.newsmodule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.any.org.newsmodule.iview.IFragment
import com.any.org.newsmodule.ui.fragment.SinaFragment
import com.any.org.newsmodule.ui.fragment.ThsFragment

/**
 *
 * @author any
 * @time 2019/10/21 15.52
 * @details
 */
class NewsFragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = mutableListOf(SinaFragment.INSTANCE, ThsFragment.INSTANCE)

    override fun getItem(position: Int): Fragment = fragmentList[position] as Fragment

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        val fragment = fragmentList[position]
        return if (fragment is IFragment) {
            fragment.getTile()
        } else ""
    }

    //回到顶部
    fun scrollTop(){
        fragmentList.forEach {
            if(it is IFragment) it.scrollToTop()
        }
    }

}