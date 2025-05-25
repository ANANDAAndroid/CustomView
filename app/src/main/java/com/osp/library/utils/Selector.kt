package com.osp.library.utils

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt

internal class Selector {
    companion object {
        internal fun colorStateList(
            @ColorInt checkedColor: Int,
            @ColorInt uncheckedColor: Int
        ): ColorStateList {
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),  // When checked
                intArrayOf(-android.R.attr.state_checked)  // When not checked
            )

            val colors = intArrayOf(checkedColor, uncheckedColor)
            return ColorStateList(states, colors)
        }


        internal fun selectorDrawable(
            @ColorInt checkedColor: Int,
            @ColorInt uncheckedColor: Int,
            @ColorInt checkedStrokeColor: Int,
            @ColorInt uncheckedStrokeColor: Int
        ): StateListDrawable {
            val checkedDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(checkedColor)
                cornerRadius = 10f
                setStroke(2, checkedStrokeColor)
            }

            val uncheckedDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(uncheckedColor)
                cornerRadius = 10f
                setStroke(2, uncheckedStrokeColor)
            }

            return StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
                addState(intArrayOf(-android.R.attr.state_checked), uncheckedDrawable)
            }
        }
    }
}