package com.infernal93.newsapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.newsapp.Interface.ItemClickListener
import com.infernal93.newsapp.ListNewsActivity
import com.infernal93.newsapp.MainActivity
import com.infernal93.newsapp.Model.WebSite
import com.infernal93.newsapp.R
import kotlinx.android.synthetic.main.source_news_layout.view.*

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

        holder!!.source_title.text = webSite.sources!![position].name

        holder.setItemClickListener(object : ItemClickListener {

            override fun onClick(view: View, position: Int) {

                val intent = Intent(context, ListNewsActivity::class.java)
                intent.putExtra("source", webSite.sources!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

               // Toast.makeText(context, "Will be implement in next tutorial", Toast.LENGTH_LONG).show()
            }
        })
    }


}