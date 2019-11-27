package com.any.org.onemodule.widget

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.any.org.onemodule.R

/**
 *
 * @author any
 * @time 2019/11/26 14.15
 * @details
 */
class TriangleLabView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val path = Path()

    private val paint = Paint()

    init {
        paint.color = Color.BLACK
        paint.isAntiAlias = true
    }


    //绘制
    override fun onDraw(canvas: Canvas?) {
        // 10 , 10
        path.moveTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.lineTo(width.toFloat(), 0f)
        path.close()
        canvas?.drawPath(path, paint)
        super.onDraw(canvas)
    }

    //开始动画
    fun animRotation(reverse: Boolean = false) {
        val startValue = if (reverse) 0f else 180f
        val endValue = if (reverse) 180f else 0f
        reverseRotation(startValue, endValue)
    }


    private fun reverseRotation(startValue: Float, endValue: Float) {
        val obj = ObjectAnimator.ofFloat(this, "rotation", startValue, endValue)
        obj.duration = 300
        obj.start()
    }


}