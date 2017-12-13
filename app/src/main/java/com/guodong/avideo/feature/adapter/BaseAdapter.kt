package com.guodong.avideo.feature.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Guodong on 2017/10/1.
 */
abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    open class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }
}

interface OnItemClickListener {
    fun onItemClick(item: Any)
}

interface OnLoadMoreListener {
    fun onLoadMore()
}
