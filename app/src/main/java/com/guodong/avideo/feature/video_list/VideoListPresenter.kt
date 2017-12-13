package com.guodong.avideo.feature.video_list

import android.content.Context
import com.guodong.avideo.feature.video.VideoActivity
import com.guodong.avideo.pojo.Category
import com.guodong.avideo.pojo.Video
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by Guodong on 2017/9/19.
 */
class VideoListPresenter(val context: Context, val videoListView: VideoListContract.View, val videoListModel: VideoListModel = VideoListModel()) : VideoListContract.Presenter {

    var category: Category = Category()
    var page = 1
    var videoList: MutableList<Video> = ArrayList<Video>()

    override fun startWork(category: Category) {
        this.category = category
        videoListView.showLoadingView(true)
        doAsync{
            videoList = videoListModel.getVideoList(this@VideoListPresenter.category)
            page = 1
            uiThread {
                videoListView.showLoadingView(false)
                videoListView.displayVideoList(videoList)
            }
        }
    }

    override fun loadMore() {
        videoListView.showLoadingView(true)
        doAsync{
            videoList = videoListModel.getVideoList(category, videoList, ++page)
            uiThread {
                videoListView.showLoadingView(false)
                videoListView.displayVideoList(videoList)
            }
        }
    }

    override fun onVideoClick(video: Video) {
        val videoIntent = VideoActivity.newIntent(context, video);
        context.startActivity(videoIntent)
    }
}