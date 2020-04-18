package com.infernal93.news.view.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.infernal93.news.R
import com.infernal93.news.view.adapter.ListSourceAdapter
import com.infernal93.news.common.Common
import com.infernal93.news.view.interfaces.NewsService
import com.infernal93.news.model.WebSite
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mService: NewsService
    lateinit var mAdapter: ListSourceAdapter
    lateinit var mDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init Paper DB cache
        Paper.init(this)

        // init Service
        mService = Common.newsService

        // init View
        swipe_to_refresh.setOnRefreshListener {

            loadWebSiteSource(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = mLayoutManager

        mDialog = SpotsDialog(this)
        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isRefresh: Boolean) {

        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
                // Read cache
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                mAdapter = ListSourceAdapter(baseContext, webSite)
                mAdapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = mAdapter
            } else {
                // Load website and write cache
                mDialog.show()
                // Fetch new data
                mService.sources.enqueue(object : retrofit2.Callback<WebSite> {

                    override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                        mAdapter = ListSourceAdapter(baseContext, response!!.body()!!)
                        mAdapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = mAdapter

                        // Save to cache
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        mDialog.dismiss()
                    }
                })
            }
        }

        else {

            swipe_to_refresh.isRefreshing = true
            // Fetch new data
            mService.sources.enqueue(object : retrofit2.Callback<WebSite> {

                override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                    mAdapter = ListSourceAdapter(baseContext, response!!.body()!!)
                    mAdapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = mAdapter
                    // Save to cache
                    Paper.book().write("cache", Gson().toJson(response.body()!!))
                    swipe_to_refresh.isRefreshing = false
                }
            })
        }
    }
}
