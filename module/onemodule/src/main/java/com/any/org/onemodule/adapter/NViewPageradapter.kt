package com.any.org.onemodule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.model.MenuModel
import com.any.org.onemodule.nui.NDetailsFragment
import com.any.org.onemodule.nui.NDetailsSericalFragment

/**
 * @author any
 * @time 2019/12/16 17.48
 * @details 新的适配器
 */
class NViewPageradapter(
    private val category: String?,
    private val list: List<MenuModel>,
    fragmentManger: FragmentManager
) :
    FragmentPagerAdapter(fragmentManger, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val map by lazy { mutableMapOf<Int, Fragment>() }
    //会缓存数据
    override fun getItem(position: Int): Fragment {
        if(map.containsKey(position)) return map[position]!!
        val fragment = NDetailsSericalFragment.getInstance(list[position].page, category)
        KLog.e("BBB", ".... ${list[position].page}   $fragment")
        map[position] = fragment
        return fragment
    }

    override fun getCount(): Int = list.size
}