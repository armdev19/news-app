package com.infernal93.news.view.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.infernal93.news.R
import com.infernal93.news.view.adapter.ListNewsAdapter
import com.infernal93.news.common.Common
import com.infernal93.news.view.interfaces.NewsService
import com.infernal93.news.model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNewsActivity : AppCompatActivity() {
    private val TAG = "ListNewsActivity"

    var mSource = ""
    var mWebHotUrl: String? = ""

    lateinit var mDialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var mAdapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        // init View
        mService = Common.newsService

        mDialog = SpotsDialog(this)

        swipe_to_refresh_news.setOnRefreshListener { loadNews(mSource, true) }

        diagonal_layout.setOnClickListener {

            val detail = Intent(baseContext, NewsDetailActivity::class.java)
            detail.putExtra("webURL", mWebHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {

            mSource = intent.getStringExtra("source")

            if (!mSource.isEmpty())

                loadNews(mSource, false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {

        if (isRefreshed) {

            mDialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        mDialog.dismiss()

                        // Get first article to hot news
                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        mWebHotUrl = response.body()!!.articles!![0].url

                        // Load all remain articles
                        val removeFirstItem = response.body()!!.articles
                        // Because we get first item to hot new, so we need remove it
                        removeFirstItem!!.removeAt(0)

                        mAdapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        mAdapter.notifyDataSetChanged()
                        list_news.adapter = mAdapter
                    }
                })
        }

        else {

            swipe_to_refresh_news.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        swipe_to_refresh_news.isRefreshing = false

                        // Get first article to hot news
                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        mWebHotUrl = response.body()!!.articles!![0].url

                        // Load all remain articles
                        val removeFirstItem = response.body()!!.articles
                        // Because we get first item to hot new, so we need remove it
                        removeFirstItem!!.removeAt(0)

                        mAdapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        mAdapter.notifyDataSetChanged()
                        list_news.adapter = mAdapter
                    }
                })
        }
    }
}
