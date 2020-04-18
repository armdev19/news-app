package com.infernal93.newsapp.Interface

import com.infernal93.newsapp.Model.News
import com.infernal93.newsapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get: GET("v2/sources?apiKey=6d802da6959a4afcae3e556b58e683f5")

    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}