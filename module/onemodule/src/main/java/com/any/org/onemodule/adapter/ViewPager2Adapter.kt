package com.any.org.onemodule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.any.org.onemodule.nui.OneVpFragment

/**
 *
 * @author any
 * @time 2019/12/16 17.48
 * @details 日期类 存在事件冲突，上下滑动时
 */
class ViewPager2Adapter(private val list: List<String>, fragmentManger: FragmentManager) :
    FragmentStateAdapter(fragmentManger) {


    override fun getItem(position: Int): Fragment {
        return OneVpFragment.getInstance(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun getTitle(position:Int) = list[position]

}