package com.stas.picker.utils.anim

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.animation.PathInterpolator
import androidx.core.graphics.PathParser
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class TransitionsHandler(
    private val fragment: Fragment
) {


    private fun getSharedAxisInterpolator() =
        PathInterpolator(
            CONTROL_X_1,
            CONTROL_Y_1,
            CONTROL_X_2,
            CONTROL_Y_2
        )

    @SuppressLint("RestrictedApi")
    private fun getContainerTransformInterpolator() =
        PathInterpolator(
            PathParser.createPathFromPathData(
                CONTAINER_INTERPOLATOR_ATTRS
            )
        )

    fun invokeSharedAxisTransitionForward() {
        fragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = SHARED_AXIS_DURATION
            interpolator = getSharedAxisInterpolator()
        }
        fragment.exitTransition =  MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = SHARED_AXIS_DURATION
            interpolator = getSharedAxisInterpolator()
        }
    }

    fun invokeSharedAxisTransitionBackward() {
        fragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = SHARED_AXIS_DURATION
            interpolator = getSharedAxisInterpolator()
        }
        fragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = SHARED_AXIS_DURATION
            interpolator = getSharedAxisInterpolator()
        }
    }

    fun invokeContainerTransformTransition() {
        fragment.exitTransition = MaterialElevationScale(false).apply {
            duration = CONTAINER_TRANSFORM_DURATION
        }
        fragment.reenterTransition = MaterialElevationScale(true).apply {
            duration = CONTAINER_TRANSFORM_DURATION
        }
    }

    fun invokeSharedElementEnterTransition() {
        fragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            interpolator = getContainerTransformInterpolator()
            duration = CONTAINER_TRANSFORM_DURATION
            setAllContainerColors(MaterialColors.getColor(fragment.requireContext(), com.google.android.material.R.attr.colorSurface, Color.BLACK))
        }
    }

    fun invokeFadeThroughTransition() {
        fragment.enterTransition = MaterialFadeThrough()
        fragment.exitTransition = MaterialFadeThrough()
    }

    fun invokeTopLevelEnterTransition() {
        fragment.enterTransition = MaterialFadeThrough()
        fragment.exitTransition = null
    }

    fun cancelTransition(){
        fragment.enterTransition = null
        fragment.returnTransition = null
    }

    companion object {
        private const val CONTROL_X_1 = 0.05f
        private const val CONTROL_Y_1 = 0.7f
        private const val CONTROL_X_2 = 0.1f
        private const val CONTROL_Y_2 = 1f
        private const val SHARED_AXIS_DURATION = 400L
        private const val CONTAINER_TRANSFORM_DURATION = 300L
        private const val CONTAINER_INTERPOLATOR_ATTRS = "M 0,0 C 0.05, 0, 0.133333, 0.06, 0.166666, 0.4 C 0.208333, 0.82, 0.25, 1, 1, 1"
    }
}