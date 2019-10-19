package com.any.org.commonlibrary.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.any.org.commonlibrary.R

/**
 * User: any
 * Date: 2019/2/28
 * 处理线条
 */

class VerticalDecoration(
    context: Context,
    private val mVerticalSpacing: Int,
    private val mFirstSpacing: Int,
    private val mLastSpacing: Int,
    @ColorRes color: Int = R.color.color_f4f4f4
) : RecyclerView.ItemDecoration() {

    private val isFirst: Boolean = (mFirstSpacing != 0)

    private val isLastExpand: Boolean = (mLastSpacing != 0)

    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    init {
        mPaint.color = context.resources.getColor(color)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.top = mVerticalSpacing
        } else if (isFirst) {
            outRect.top = mFirstSpacing
        }
        val lastPosition = parent.adapter?.itemCount ?: 0
        if (position == lastPosition - 1 && isLastExpand) {  //多预留空间
            outRect.bottom = mLastSpacing
        }


    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val lastPosition = parent.adapter?.itemCount ?: 0
        for (i in 0 until childCount - 1) {   //下标从0开始  如果有15个view 则是0..14 所以最后一个是14
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            //绘制
            drawTopRect(view, left.toFloat(), right.toFloat(), c, position, lastPosition - 1)
        }
    }


    //绘制topRect
    private fun drawTopRect(
        view: View,
        left: Float,
        right: Float,
        c: Canvas,
        position: Int,
        lastPosition: Int
    ) {
        //如果是第一个并且发现是有高度的，则要单独处理
        if (position == 0 && isFirst) {
            var top = view.top - mFirstSpacing
            var bottom = view.top
            c.drawRect(left, top.toFloat(), right, bottom.toFloat(), mPaint)
        }
        //注意，其实这些都是倒着绘制的， 第一个bottom是第二个的top
        val top = view.bottom
        val bottom = top + mVerticalSpacing
        c.drawRect(left, top.toFloat(), right, bottom.toFloat(), mPaint)

        // 最后一个要加上本身的高度，然后还要加上top的高度
        if (position + 1 == lastPosition && isLastExpand) {
            val top = view.bottom + view.height + mVerticalSpacing
            val bottom = top + mLastSpacing
            c.drawRect(left, top.toFloat(), right, bottom.toFloat(), mPaint)
        }
    }

}
