package com.any.org.onemodule.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.os.Vibrator
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.any.org.onemodule.R

/**
 *
 * @author any
 * @time 2019/8/9 15.28
 * @details  播放动画view
 */
class ChatAnimImageView : ImageView {

    //动画 默认ID
    private var mDrawableId = arrayOf(R.drawable.ic_voice_v_1,R.drawable.ic_voice_v_2,R.drawable.ic_voice_v_3)

    private var objectAnim: ValueAnimator? = null

    private var isStart = false

    private var currIndex = 2

    constructor(context: Context?) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    //设置Ids
    fun setDrawableIds(ids:Array<Int>){
        mDrawableId = ids
    }


    fun startAnim() {
        if (isStart) return
        objectAnim = ValueAnimator.ofInt(0, 3)
        objectAnim?.repeatCount = INFINITE  //无限循环
        objectAnim?.repeatMode = RESTART   //重头开始
        objectAnim?.addUpdateListener {
            val index = it.animatedValue as Int
            if(index == currIndex || index >2) return@addUpdateListener
            currIndex = index
            setPlayer()
        }
        objectAnim?.duration = 1500
        objectAnim?.start()
        isStart = true
    }

    fun stopAnim() {
        objectAnim?.cancel()
        isStart = false
        currIndex = 2
        //最后一张
        setPlayer()
    }


    private fun setPlayer() {
        //取出一张图片
        setImageResource(mDrawableId[currIndex])
    }
}

//震动效果
fun View.vibrate() {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(200)
}