package com.any.org.onemodule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog

//fun <T> ArrayList<T>?.newInstance(): ArrayList<T> = this ?: arrayListOf()
/**
 * @author any
 * @time 2019/11/26 17.13
 * @details
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listData by lazy { ArrayList<T>() }

    fun setNewData(data: List<T>?) {
        this.listData.apply {
            clear()
            data?.let { dt ->
                addAll(dt)
            }
        }
        notifyDataSetChanged()
    }


    fun addData(data: List<T>?) {
        this.listData.apply {
            data?.let { dt ->
                addAll(dt)
            }
        }
        notifyDataSetChanged()
    }

    fun addData(data: T?) {
        this.listData.apply {
            data?.let { dt ->
                add(dt)
            }
        }
        notifyDataSetChanged()
    }




    fun getItemModel(position: Int): T? =
        if (position >= 0 && position < listData.size) listData[position] else null

    override fun getItemViewType(position: Int): Int {
        val oneDM = listData?.get(position)
        return oneDM?.let {
            getViewType(it)
        } ?: super.getItemViewType(position)
    }

    abstract fun getViewType(t: T): Int

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val oneData = listData?.get(position)
        oneData?.let {
            (holder as? BaseItemView<*>)?.setData(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vm = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context.applicationContext),getLayoutId(viewType),parent,false)
        return BaseItemView(vm)
    }

    @LayoutRes
    abstract fun getLayoutId(viewType:Int):Int

    abstract fun handlerVariable(dataBind: ViewDataBinding, t: T)

    //给外层重写调用，并不是所有UI需要事件
    open fun onClickItem(t:T){

    }

    inner class BaseItemView<VM:ViewDataBinding>(private val  vm:VM) : RecyclerView.ViewHolder(vm.root) {

        fun setData(t: T) {
            itemView.viewOnClick {
                onClickItem(t)
            }
            handlerVariable(vm, t)
            vm.executePendingBindings()
        }
    }

}


