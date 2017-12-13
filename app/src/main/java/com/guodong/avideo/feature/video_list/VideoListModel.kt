package com.guodong.avideo.feature.video_list

import com.guodong.avideo.pojo.Category
import com.guodong.avideo.pojo.Video
import com.guodong.avideo.utils.BaseUrl
import java.net.URL

/**
 * Created by Guodong on 2017/9/19.
 */
class VideoListModel {

    companion object {
        val videoListRegex = Regex("<a href=\"/([^\\\"]*)\">\\s*.*\\s*<img src=\"([^\\\"]*)\"\\s*title=\"([^\\\"]*)\".*\\s*<div class=\"duration\">\\s*(.*)\\s*<\\/div>")
    }

    fun getVideoList(category: Category, videoList: MutableList<Video> = ArrayList<Video>(), page: Int = 1): MutableList<Video> {
        val videoListUrl = "${BaseUrl.baseUrl}/videos/${category.id}?page=$page"
        val videosBody = URL(videoListUrl).readText()
        return findVidelHtmlList(videosBody, videoList)
    }

    private fun findVidelHtmlList(htmlBody: String, videoList: MutableList<Video>): MutableList<Video> {
        for(matchResult in videoListRegex.findAll(htmlBody)) {
            videoList.add(Video(matchResult.groupValues[1], matchResult.groupValues[2], matchResult.groupValues[3], matchResult.groupValues[4]))
        }
        return videoList
    }
}