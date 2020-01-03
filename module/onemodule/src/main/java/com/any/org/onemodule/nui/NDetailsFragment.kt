package com.any.org.onemodule.nui

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.get
import com.any.org.ankolibrary.myArgument
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBFragmentEx
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.obser.AdapterDataObserver
import com.any.org.onemodule.adapter.obser.CommonAdapter
import com.any.org.onemodule.databinding.NuiDetailsFragmentBinding
import com.any.org.onemodule.manager.VoicePlayerManager
import com.any.org.onemodule.model.CommentItemModel
import com.any.org.onemodule.model.ContentDetailsModel
import com.any.org.onemodule.nviewmodel.NDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/12/31 19.48
 * @details 新的详情页面
 */
open class NDetailsFragment : BaseVBFragmentEx<NuiDetailsFragmentBinding>() {

    // 根据不同的
    private val detailVM: NDetailsViewModel by viewModel()

    private val itemId by myArgument<String>("id")
    private val category by myArgument<String>("cate")

    private val commAdapter by lazy { CommonAdapter(getCommentList()) }

    val baseModel = MutableLiveData<ContentDetailsModel>()

    companion object {
        fun getInstance(itemId: String?, category: String?): Fragment {
            val bundle = Bundle()
            bundle.putString("id", itemId)
            bundle.putString("cate", category)
            val f = NDetailsFragment()
            f.arguments = bundle
            KLog.e("init.... $itemId  $category")
            return f
        }
    }

    override fun getResourceId(): Int = R.layout.nui_details_fragment

    override fun initData() {
        setVM()
        //绑定
        mBinding?.adapter = commAdapter
        // VM
        mBinding?.vm?.let {
            get(it.baseModel) { cmd ->
                baseModel.set(cmd)
                KLog.e("BBB", "获取到的 baseModel...${baseModel}   $itemId  it=${cmd?.commentnum} ")
            }
        }
    }

    open fun getCommentList(): AdapterDataObserver<CommentItemModel> = detailVM.commentList

    open fun setVM() {
        mBinding?.vm = detailVM
    }

    override fun lazyData() {
        loadContentData()
    }


    // 动态加载指定数据
    private fun loadContentData() {
        KLog.e("lazyData...   $itemId  $category  vm=${mBinding?.vm}")
        itemId?.let {
            mBinding?.vm?.getDetailsContent(category, it)
        }
    }


    private val offValue: (Int?, Int) -> Int = { x, y ->
        x?.let {
            it - y
        } ?: 0
    }

    override fun initEvent() {
        val contentV = mBinding?.contentLl
        mBinding?.scrollView?.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val isLoadMore = commAdapter.loadMoreData
            KLog.e("AAA", ".... loadMore     $isLoadMore")
            //加载更多
            if (isLoadMore && offValue(contentV?.height, scrollY) == v?.height) {
                mBinding?.vm?.getComment()
            }
        }
        //处理
        commAdapter.setOnLoadMoreListener({
            KLog.e(".... loadMore")
        }, mBinding?.commentRv)
    }


    override fun onDestroyView() {
        val viewGroup = mBinding?.commentRv?.parent as? ViewGroup
        viewGroup?.removeAllViews()
        mBinding?.vm?.onDestroy()
        mBinding?.commentRv?.adapter = null
        VoicePlayerManager.vPlayer.onStopCallBack = null
        VoicePlayerManager.vPlayer.onDestroy()
        KLog.e("AAA", "...onDestroyView")
        super.onDestroyView()
    }


    override fun onDestroy() {
        KLog.e("AAA", "over... kk:  ${mBinding?.vm}")
        super.onDestroy()
    }


    override fun onDetach() {
        super.onDetach()
        KLog.e("AAA", "msg..... onDetach")
    }

}