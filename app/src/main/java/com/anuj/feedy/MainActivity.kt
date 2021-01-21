package com.anuj.feedy

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter : NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //to remove the action bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar()?.hide();

        setContentView(R.layout.activity_main)

        //news app

        //1)put the recycler view in the activity_main.xml
        //2)create the layoutManager in MainActivity
        //3)create the adapter class along with the view holder
        //4)make the items clickable and using callBack(interface) inform MainActivity about the click

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
        mAdapter = NewsListAdapter( this)    //getting the adapter
        recyclerView.adapter = mAdapter

        btnRefresh.setOnClickListener {
            fetchData()
        }

    }

    private fun fetchData(){

        //show the progress bar
        //progressBar.visibility = View.VISIBLE

        //val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=9c2e552d3bc344d6a62264ae80b8d4a8"
        val url = "http://api.mediastack.com/v1/news?access_key=24e91dc58c3be7598442fda93e5de777&countries=in"

        val jsonObjectRequest =JsonObjectRequest(Request.Method.GET , url , null,
            {
                //get the JsonArray articles containing multiple JsonObject (individual articles)
                val newsJsonArray = it.getJSONArray("data")
                val newsArray = ArrayList<News>()
                //iterate in the JsonArray and put all individual article in newsArray
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("author"),
                            newsJsonObject.getString("url"),
                            newsJsonObject.getString("image")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}