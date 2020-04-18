package com.infernal93.news.common

import com.infernal93.news.view.interfaces.NewsService
import com.infernal93.news.remote.RetrofitClient

object Common {

    private const val BASE_URL = "https://newsapi.org/"
    private const val API_KEY = "6d802da6959a4afcae3e556b58e683f5"

    val newsService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String) : String {

        return StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
    }
}