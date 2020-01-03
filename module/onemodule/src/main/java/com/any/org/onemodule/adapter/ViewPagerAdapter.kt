package com.any.org.onemodule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.any.org.onemodule.nui.OneVpFragment

/**
 *
 * @author any
 * @time 2019/12/16 17.48
 * @details 日期类
 */
class ViewPagerAdapter(private val list: List<String>, fragmentManger: FragmentManager) :
    FragmentPagerAdapter(fragmentManger, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OneVpFragment.getInstance(list[position])
    }

    override fun getCount(): Int  = list.size

    fun getTitle(position:Int) = list[position]

}