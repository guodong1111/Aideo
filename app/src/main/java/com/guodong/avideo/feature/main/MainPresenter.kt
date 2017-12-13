package com.guodong.avideo.feature.main

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Guodong on 2017/9/19.
 */
class MainPresenter(val mainView: MainContract.View, val categoryModel_1: CategoryModel = CategoryModel()) : MainContract.Presenter {

    override fun startWork() {
        mainView.showLoadingView(true)
        doAsync{
            val categoryList = categoryModel_1.getCategoryList()
            uiThread {
                mainView.showLoadingView(false)
                mainView.displayCategory(categoryList)
            }
        }
    }
}