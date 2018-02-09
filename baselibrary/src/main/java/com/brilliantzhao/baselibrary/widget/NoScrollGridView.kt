package com.brilliantzhao.baselibrary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

/**
 * description:不会滚动的gridview
 * Created by xsf
 * on 2016.04.15:04
 */
class NoScrollGridView : GridView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
