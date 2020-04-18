package com.infernal93.newsapp.Common

import com.infernal93.newsapp.Interface.NewsService
import com.infernal93.newsapp.Remote.RetrofitClient

object Common {

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "6d802da6959a4afcae3e556b58e683f5"

    val newsService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String) : String {

        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()

        return apiUrl
    }
}