package com.brilliantzhao.baselibrary.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View

/**
 * description:
 * Date: 2017/11/30 0030 10:44
 * User: BrilliantZhao
 */
class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                               defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    internal var width: Float = 0.toFloat()
    internal var height: Float = 0.toFloat()

    init {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    override fun onDraw(canvas: Canvas) {

        if (width > 12 && height > 12) {
            val path = Path()
            path.moveTo(12f, 0f)
            path.lineTo(width - 12, 0f)
            path.quadTo(width, 0f, width, 12f)
            path.lineTo(width, height - 12)
            path.quadTo(width, height, width - 12, height)
            path.lineTo(12f, height)
            path.quadTo(0f, height, 0f, height - 12)
            path.lineTo(0f, 12f)
            path.quadTo(0f, 0f, 12f, 0f)
            canvas.clipPath(path)
        }

        super.onDraw(canvas)
    }
}