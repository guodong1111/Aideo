package com.guodong.avideo.utils

import android.content.Context
import java.io.File


/**
 * Created by tony on 2017/10/7.
 */
object FileUtils {

    fun getOutputCacheFile(context: Context, fileName: String): File {
        try {
            return File(context.getExternalCacheDir(), fileName)
        } catch (e: Exception) {
            return File(context.getCacheDir(), fileName)
        }

    }
}