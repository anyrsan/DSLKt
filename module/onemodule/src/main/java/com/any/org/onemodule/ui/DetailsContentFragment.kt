package com.any.org.onemodule.ui

import androidx.core.widget.NestedScrollView
import com.any.org.ankolibrary.argument
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBFragmentEx
import com.any.org.commonlibrary.nui.finish
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.obser.CommonAdapter
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.databinding.DetailsContentFragmentBinding
import com.any.org.onemodule.manager.VoicePlayerManager
import com.any.org.onemodule.viewmodel.DetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/12/12 19.20
 * @details 阅读详情
 */
class DetailsContentFragment : BaseVBFragmentEx<DetailsContentFragmentBinding>(), IAdjustDensity {

    // 根据不同的
    private val detailVM: DetailsViewModel by viewModel()

    private val itemId by argument<String>("itemId")

    private val category by argument<String>("category")

    private val commAdapter by lazy { CommonAdapter(detailVM.commentList) }

    override fun getResourceId(): Int = R.layout.details_content_fragment

    override fun initData() {
        //绑定vm
        mBinding?.vm = detailVM
        //处理titleTypeName
        mBinding?.titleTypeName = CateApi.getCateTitle(category, false)
        //绑定
        mBinding?.adapter = commAdapter
    }

    override fun lazyData() {
        KLog.e("lazyData...   $itemId  $category")
        itemId?.let {
            detailVM.getDetailsContent(category, it)
        }
    }

    private val offValue: (Int?, Int) -> Int = { x, y ->
        x?.let {
            it - y
        } ?: 0
    }

    override fun initEvent() {
        mBinding?.backIv?.viewOnClick {
            finish()
        }
        val contentV = mBinding?.contentLl
        mBinding?.scrollView?.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val isLoadMore = commAdapter.loadMoreData
            KLog.e(".... loadMore     $isLoadMore")
            //加载更多
            if (isLoadMore && offValue(contentV?.height, scrollY) == v?.height) {
                detailVM.getComment()
            }
        }
        //处理
        commAdapter.setOnLoadMoreListener({
            KLog.e(".... loadMore")
        }, mBinding?.commentRv)
    }


    override fun onDestroy() {
        //停止动画，不然存在内存泄漏
        mBinding?.vm?.animPlay?.set(false)
        VoicePlayerManager.vPlayer.onStopCallBack = null
        VoicePlayerManager.vPlayer.onDestroy()
        super.onDestroy()
    }

    override fun pxInDp(): Int = 360
}