package com.stas.picker.utils.anim

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import com.stas.picker.utils.logger.Logger
import com.stas.picker.utils.extension.visible

fun showAnimation(view: View, visibility: Boolean) {
    val transition = Slide(Gravity.BOTTOM).apply {
        duration = ANIMATION_DURATION
        addTarget(view)
    }.addListener(object : TransitionListenerAdapter() {
        override fun onTransitionStart(transition: Transition) {
            super.onTransitionStart(transition)
            Logger.log("anim is going")
        }

        override fun onTransitionEnd(transition: Transition) {
            super.onTransitionEnd(transition)
        }
    })
    TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
    view.visible(visibility)
}

private const val ANIMATION_DURATION = 200L