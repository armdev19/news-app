package com.infernal93.news.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.news.view.interfaces.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var mSourceTitle: TextView = itemView.source_news_name

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener

    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)
    }
}