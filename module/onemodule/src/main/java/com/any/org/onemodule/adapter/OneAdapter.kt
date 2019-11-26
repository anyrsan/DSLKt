package com.any.org.onemodule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.any.org.onemodule.model.OneDataItemModel
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.databinding.OneDtItemAdapterBinding
import com.any.org.onemodule.databinding.OneFxItemAdapterBinding
import com.any.org.onemodule.databinding.OneYyItemAdapterBinding
import com.any.org.onemodule.databinding.OneZxItemAdapterBinding

/**
 *
 * @author any
 * @time 2019/11/25 13.58
 * @details
 */
class OneAdapter(private var listData: List<OneDataItemModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //重新设置数据
    fun setData(listData: List<OneDataItemModel>?) {
        this.listData = listData
        notifyDataSetChanged()
    }


    private fun makeView(parent: ViewGroup, @LayoutRes rId: Int) =
        LayoutInflater.from(parent.context).inflate(rId, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = makeView(parent, R.layout.one_fx_item_adapter)
                BaseItemView<OneFxItemAdapterBinding>(itemView)
            }
            4 -> {
                val itemView = makeView(parent, R.layout.one_yy_item_adapter)
                BaseItemView<OneYyItemAdapterBinding>(itemView)
            }
            8 -> {
                val itemView = makeView(parent, R.layout.one_dt_item_adapter)
                BaseItemView<OneDtItemAdapterBinding>(itemView)
            }
            else -> {
                val itemView = makeView(parent, R.layout.one_zx_item_adapter)
                BaseItemView<OneZxItemAdapterBinding>(itemView)
            }
        }
    }

    override fun getItemCount(): Int = listData?.size ?: 0


    override fun getItemViewType(position: Int): Int {
        val oneDM = listData?.get(position)
        return oneDM?.let {
            CateApi.getType(it.category)
        } ?: super.getItemViewType(position)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val oneData = listData?.get(position)
        oneData?.let {
            if (holder is BaseItemView<*>) {
                holder.setData(it)
            }
        }
    }


    class BaseItemView<VM : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dataBind by lazy { DataBindingUtil.bind<VM>(itemView) }

        fun setData(oneData: OneDataItemModel) {
            dataBind?.setVariable(BR.oneDM, oneData)
            dataBind?.setVariable(BR.auther,oneData.author)
            dataBind?.executePendingBindings()
        }

    }


}