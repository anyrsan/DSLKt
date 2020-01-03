package com.any.org.onemodule.nui

import androidx.viewpager.widget.ViewPager
import com.any.org.ankolibrary.argument
import com.any.org.ankolibrary.get
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBActivityEx
import com.any.org.commonlibrary.nui.addFragment
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.commonlibrary.ui.setTopPadding
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.NViewPageradapter
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.databinding.NuiDetailsActivityBinding
import com.any.org.onemodule.model.MenuModel
import com.any.org.onemodule.nviewmodel.NMenuViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/12/11 14.21
 * @details 处理详情页
 */
class NDetailsActivity : BaseVBActivityEx<NuiDetailsActivityBinding>(), IAdjustDensity {

    private val itemId by argument<String>("itemId")

    private val category by argument<String>("category")

    private val listMenu = mutableListOf<MenuModel>()
    //用来控制是否为连载的
    private var isHasMenu = false

    private val contentAdapter by lazy {
        NViewPageradapter(
            category,
            listMenu,
            supportFragmentManager
        )
    }

    private val detailVM: NMenuViewModel by viewModel()

    override fun getResourceId(): Int = R.layout.nui_details_activity

    override fun initView() {
        //处理titleTypeName
        mBinding.titleTypeName = CateApi.getCateTitle(category, false)
        mBinding.mainRL.setTopPadding()
        //适配器
        mBinding.contentVP.adapter = contentAdapter
        mBinding.contentVP.offscreenPageLimit = 3
    }

    //设置数据
    fun setData(commentnum: String, praisenum: String, sharenum: String) {
        mBinding.commentnum = commentnum
        mBinding.praisenum = praisenum
        mBinding.sharenum = sharenum
    }


    override fun initGetIntent() {

    }

    override fun initData() {
        mBinding.isHasMenu = category == "2"
        when (category) {
            "2" -> {  //  连载
                isHasMenu = true
                get(detailVM.listMenu) {
                    it?.let { list ->
                        listMenu.addAll(list)
                        contentAdapter.notifyDataSetChanged()
                        mBinding.contentVP.setCurrentItem(list.size - 1, false)
                    }
                }
            }
            "3" -> {  //   问答
                isHasMenu = false
                val fragment = NDetailsQTFragment.getInstance(itemId,category) as NDetailsQTFragment
                get(fragment.baseModel) { cmd ->
                    if (cmd != null) {
                        setData("${cmd.commentnum}", "${cmd.praisenum}", "${cmd.sharenum}")
                    }
                }
                addFragment(fragment, R.id.contentFl)
            }
            else -> { // 普通详情
                isHasMenu = false
                val fragment:NDetailsFragment = NDetailsFragment.getInstance(itemId,category) as NDetailsFragment
                get(fragment.baseModel) { cmd ->
                    if (cmd != null) {
                        setData("${cmd.commentnum}", "${cmd.praisenum}", "${cmd.sharenum}")
                    }
                }
                addFragment(fragment, R.id.contentFl)
            }
        }
    }

    override fun initEvent() {
        //关闭
        mBinding.backIv.viewOnClick {
            finish()
        }
        //监听滑动
        mBinding.contentVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                //获取page
                val fragment = contentAdapter.getItem(position) as NDetailsSericalFragment
                val cmd = fragment.baseModel.value
                KLog.e("BBB","当前  $position    cmd=$cmd        model=${fragment.baseModel}")
                if (cmd != null) {
                    setData("${cmd.commentnum}", "${cmd.praisenum}", "${cmd.sharenum}")
                } else {
                    get(fragment.baseModel) { cmd ->
                        KLog.e("BBB","输出监听 。(获取到的)。 $cmd")
                        if (cmd != null) {
                            setData("${cmd.commentnum}", "${cmd.praisenum}", "${cmd.sharenum}")
                        }
                    }
                }

            }
        })
    }

    override fun lazyData() {
        if (isHasMenu) {
            itemId?.let {
                detailVM.getSerialMenu(it)
            }
        }
    }

    override fun pxInDp(): Int = 360
}