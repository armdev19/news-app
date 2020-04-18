package com.infernal93.news.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.curioustechizen.ago.RelativeTimeTextView
import com.infernal93.news.view.interfaces.ItemClickListener
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var mArticleTitle: TextView = itemView.article_title
    var mArticleTime: RelativeTimeTextView = itemView.article_time
    var mArticleImage: CircleImageView = itemView.article_image

    init {

        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {

        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View) {

        itemClickListener.onClick(v, adapterPosition)
    }
}