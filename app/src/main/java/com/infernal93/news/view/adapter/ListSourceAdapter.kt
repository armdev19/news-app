package com.infernal93.news.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.news.view.interfaces.ItemClickListener
import com.infernal93.news.view.activities.ListNewsActivity
import com.infernal93.news.model.WebSite
import com.infernal93.news.R

class ListSourceAdapter(private val context: Context, private val webSite: WebSite) : RecyclerView.Adapter<ListSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.source_news_layout, parent, false)

        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return webSite.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {

        holder!!.mSourceTitle.text = webSite.sources!![position].name

        holder.setItemClickListener(object : ItemClickListener {

            override fun onClick(view: View, position: Int) {

                val intent = Intent(context, ListNewsActivity::class.java)
                intent.putExtra("source", webSite.sources!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })
    }
}