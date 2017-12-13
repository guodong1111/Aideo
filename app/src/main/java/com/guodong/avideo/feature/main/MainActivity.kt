package com.guodong.avideo.feature.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.guodong.avideo.R
import com.guodong.avideo.feature.video_list.VideoListFragment
import com.guodong.avideo.pojo.Category
import com.guodong.avideo.utils.showLoadingView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View  {
    val mainPresenter = MainPresenter(this)
    val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categoryPager.adapter = categoryAdapter
        mainPresenter.startWork()
    }

    override fun displayCategory(categoryList: List<Category>) {
        categoryAdapter.updateCategoryList(categoryList)
        categoryTab.setupWithViewPager(categoryPager)
    }

    override fun showLoadingView(visibility: Boolean) {
        categoryProgressBar?.showLoadingView(visibility)
    }
}

/**
 * Created by Guodong on 2017/9/19.
 */
class CategoryAdapter(fragmentManager: FragmentManager, var categoryList: List<Category> = ArrayList<Category>()) : FragmentPagerAdapter(fragmentManager) {

    fun updateCategoryList(newCategoryList: List<Category>) {
        categoryList = newCategoryList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = categoryList.size

    override fun getItem(position: Int): VideoListFragment {
        val videoListFragment = VideoListFragment()
        val bundle = Bundle()
        bundle.putParcelable(VideoListFragment.BUNDLE_NAME_CATEGORY, categoryList[position])
        videoListFragment.arguments = bundle
        return videoListFragment
    }

    override fun getPageTitle(position: Int): CharSequence = categoryList[position].name

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }
}

/**
 * Created by Guodong on 2017/9/19.
 */
interface MainContract {

    interface View {
        fun showLoadingView(visibility: Boolean)
        fun displayCategory(categoryList: List<Category>)
    }

    interface Presenter {
        fun startWork()
    }
}