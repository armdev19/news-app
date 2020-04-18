package com.infernal93.news.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.news.common.ISO8601Parser
import com.infernal93.news.view.interfaces.ItemClickListener
import com.infernal93.news.model.Article
import com.infernal93.news.view.activities.NewsDetailActivity
import com.infernal93.news.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter (val articleList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<ListNewsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)

        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {

       return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        // Load Image
        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.mArticleImage)

        if (articleList[position].title!!.length > 65) {

            holder.mArticleTitle.text = articleList[position].title!!.substring(0, 65) + "..."
        } else {

            holder.mArticleTitle.text = articleList[position].title!!
        }

        if (articleList[position].publishedAt != null) {

            var date: Date? = null
            try {

                date = ISO8601Parser.parse(articleList[position].publishedAt!!)

            } catch (ex: ParseException) {

                ex.printStackTrace()
            }

            holder.mArticleTime.setReferenceTime(date!!.time)
        }

        // Set Event Click
        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val detail = Intent(context, NewsDetailActivity::class.java)
                detail.putExtra("webURL", articleList[position].url)
                detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)
            }
        })
    }
}