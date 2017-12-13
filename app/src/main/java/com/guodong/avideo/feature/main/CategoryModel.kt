package com.guodong.avideo.feature.main

import com.guodong.avideo.pojo.Category
import com.guodong.avideo.utils.BaseUrl
import java.net.URL

/**
 * Created by Guodong on 2017/9/19.
 */
class CategoryModel {

    companion object {
        val categoryListRegex = Regex("<a href=\"/videos/?([^\"]*)\" class=\"list-group-item\">\\s*([^\"]*)\\s*</a>")
    }

    fun getCategoryList(): List<Category> {
        val url = "${BaseUrl.baseUrl}/videos"
        val videosBody = URL(url).readText()
        return findCategoryHtmlList(videosBody)
    }

    private fun findCategoryHtmlList(htmlBody: String): List<Category> {
        val categoryList = ArrayList<Category>()
        for(matchResult in categoryListRegex.findAll(htmlBody)) {
            categoryList.add(Category(matchResult.groupValues[1], matchResult.groupValues[2]))
        }
        return categoryList
    }
}