package com.guodong.avideo.feature.video

import android.content.Context
import android.net.Uri
import com.guodong.avideo.pojo.Video
import com.guodong.avideo.utils.BaseUrl
import java.net.URL

/**
 * Created by Guodong on 2017/9/19.
 */
class VideoModel(val context: Context) {

    companion object {
        val fileName = "cacheVideo.m3u8"
        val videoRegex = Regex("<source src=\"([^\"]*)\"")
    }

    fun getVideoUri(video: Video): Uri {
        val contentUrl = "${BaseUrl.baseUrl}/${video.id}"
        val videosBody = URL(contentUrl).readText()
        val videoM3u8Url = findVideoUrlHtmlList(videosBody)
        return Uri.parse(videoM3u8Url)
    }

    private fun findVideoUrlHtmlList(htmlBody: String): String? {
        return videoRegex.find(htmlBody)?.groupValues?.get(1)
    }
}