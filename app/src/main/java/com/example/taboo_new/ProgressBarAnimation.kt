package com.example.taboo_new

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView

class ProgressBarAnimation(
    var context: Context,
    var progressBar: ProgressBar,
    var texView: TextView,
    var from: Float,
    var to: Float
) :
    Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
        texView.text = "${value.toInt()} %"
        if (value == to) {

            context.startActivity(Intent(context, MenuActivity::class.java))
        }
    }
}