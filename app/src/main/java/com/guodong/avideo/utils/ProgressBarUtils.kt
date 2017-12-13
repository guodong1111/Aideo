package com.guodong.avideo.utils

import android.view.View
import android.widget.ProgressBar

/**
 * Created by Guodong on 2017/9/20.
 */
fun ProgressBar.showLoadingView(show: Boolean) {
    visibility = if(show) View.VISIBLE else View.GONE
}