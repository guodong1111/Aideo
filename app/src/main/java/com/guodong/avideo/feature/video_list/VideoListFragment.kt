package com.guodong.avideo.feature.video_list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.guodong.avideo.R
import com.guodong.avideo.feature.adapter.OnItemClickListener
import com.guodong.avideo.feature.adapter.OnLoadMoreListener
import com.guodong.avideo.pojo.Category
import com.guodong.avideo.pojo.Video
import com.guodong.avideo.utils.showLoadingView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_video_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Guodong on 2017/9/20.
 */
class VideoListFragment() : Fragment(), VideoListContract.View {

    companion object {
        val BUNDLE_NAME_CATEGORY = "category"
    }

    val videoListPresenter by lazy { VideoListPresenter(context, this) }
    val videoListAdapter = VideoListAdapter(onItemClickListener = object : OnItemClickListener {

        override fun  onItemClick(item: Any) {
            when (item) {
                is Video -> videoListPresenter.onVideoClick(item)
            }
        }

    }, onLoadMoreListener = object : OnLoadMoreListener {

        override fun onLoadMore() {
            videoListPresenter.loadMore()
        }

    })

    val category: Category by lazy { arguments.getParcelable<Category>(BUNDLE_NAME_CATEGORY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoListPresenter.startWork(category)
        videoListRecyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        videoListRecyclerView.adapter = videoListAdapter
    }

    override fun displayVideoList(videoList: List<Video>) {
        videoListAdapter.updateVideoList(videoList)
    }

    override fun showLoadingView(visibility: Boolean) {
        videoListProgressBar?.showLoadingView(visibility)
    }
}

class VideoListAdapter(var videoList: List<Video> = ArrayList<Video>(), val onItemClickListener: OnItemClickListener, val onLoadMoreListener: OnLoadMoreListener) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    fun updateVideoList(newVideoList: List<Video>) {
        videoList = newVideoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = videoList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_video_list, null), onItemClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        holder.itemView.tag = video
        Picasso.with(holder.videoImageView.context)
                .load(video.imageUrl)
                .fit().centerCrop()
                .into(holder.videoImageView)
        holder.videoTitleView.text = video.title
        if(position >= videoList.size - 1) onLoadMoreListener.onLoadMore()
    }

    class ViewHolder(view: View, onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val videoImageView = view.findViewById<ImageView>(R.id.videoImageView)
        val videoTitleView = view.findViewById<TextView>(R.id.videoTitleView)

        init {
            view.onClick {
                onItemClickListener.onItemClick(if(null != view.tag) view.tag else Unit)
            }
        }
    }

}

/**
 * Created by Guodong on 2017/9/19.
 */
interface VideoListContract {

    interface View {
        fun displayVideoList(videoList: List<Video>)
        fun showLoadingView(visibility: Boolean)
    }

    interface Presenter {
        fun startWork(category: Category)
        fun onVideoClick(video: Video)
        fun loadMore()
    }
}