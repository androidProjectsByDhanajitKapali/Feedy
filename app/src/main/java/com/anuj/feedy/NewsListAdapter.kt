package com.anuj.feedy

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter(private val listner :NewsItemClicked) :
        RecyclerView.Adapter<NewsViewHolder>() {

    //placing it inside as NewsAdapter will get this array list after the data in its is being
    //fetched from the internet
    private val items :ArrayList<News> = ArrayList()

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()  //to update all the items by calling below all 3 fun() again-again
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        //called when a new view holder is created ,it is called for the no of items in the list
        // view

        //inflater is used to convert xml to a view
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,
                false)

        //setting up click on the item
        val viewHolder  = NewsViewHolder(view)
        view.setOnClickListener {
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
        //return NewsViewHolder(view) //will crash the app
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        //getting the progress bar
        //val progressBar = holder.progressBar

        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.image)
                /*
                //trying to implement the progress bar

            .listener(
            object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    progressBar.visibility = View.GONE  //hide the progress bar
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE    //hide the progess bar
                    return false
                }
            }
        ) */
        .into(holder.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}

interface NewsItemClicked {
    fun onItemClicked(item:News)
}

class NewsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val titleView  = itemView.findViewById<TextView>(R.id.title)
    val image  = itemView.findViewById<ImageView>(R.id.imgNews)
    val author  = itemView.findViewById<TextView>(R.id.author)

    //val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
}