package com.stas.picker.bottom_sheet.media_adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class RecyclerItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val spacing = (spacing * parent.context.resources.displayMetrics.density).roundToInt()
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = 4
        outRect.right = 4

        outRect.top = 4
        outRect.bottom = 4
    }
}