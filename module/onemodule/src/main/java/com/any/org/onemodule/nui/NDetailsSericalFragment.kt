package com.any.org.onemodule.nui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.adapter.obser.AdapterDataObserver
import com.any.org.onemodule.model.CommentItemModel
import com.any.org.onemodule.nviewmodel.NSericalViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/12/12 19.20
 * @details 问答
 */
class NDetailsSericalFragment : NDetailsFragment() {


    companion object {
        fun getInstance(itemId: String?, category: String?): Fragment {
            val bundle = Bundle()
            bundle.putString("id", itemId)
            bundle.putString("cate", category)
            val f = NDetailsSericalFragment()
            f.arguments = bundle
            KLog.e("init.... $itemId  $category")
            return f
        }
    }

    // 根据不同的
    private val detailVM: NSericalViewModel by viewModel()

    override fun setVM() {
        mBinding?.vm = detailVM
    }

    override fun getCommentList(): AdapterDataObserver<CommentItemModel> = detailVM.commentList


}