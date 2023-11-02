package com.stas.picker.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class PaddingDecorator (
    private val divider: Int
) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val padding = (divider * parent.context.resources.displayMetrics.density).roundToInt()
        with(outRect){
            left = padding
            right = padding
            top = padding
            bottom = padding
        }
    }
}