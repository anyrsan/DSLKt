package com.any.org.onemodule.adapter.obser

import androidx.databinding.ViewDataBinding
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.CommentItemAdapterBinding
import com.any.org.onemodule.model.CommentItemModel

/**
 *
 * @author any
 * @time 2019/12/13 15.50
 * @details
 */
class CommonAdapter(list:AdapterDataObserver<CommentItemModel>) :BaseLoadAdapter<CommentItemModel,CommentItemAdapterBinding>(R.layout.comment_item_adapter,list) {

    override fun handlerData(binding: ViewDataBinding?, item: CommentItemModel?) {
        binding?.setVariable(BR.cModel,item)
    }

}