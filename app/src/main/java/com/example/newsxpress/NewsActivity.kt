package com.example.newsxpress

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private var articles = mutableListOf<Articles>();
    var pageNub = 1;
    var totalResult = -1;
    val TAG = "Mitesh"

    lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        getNews()
        adapter = NewsAdapter(this@NewsActivity, articles)
        val newsList = findViewById<RecyclerView>(R.id.newsList)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@NewsActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.RIGHT_TO_LEFT)
        layoutManager.setPagerMode(true);
        layoutManager.setPagerFlingVelocity(3000);
        newsList.layoutManager = layoutManager
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(colorPicker.getColor()))
                Log.d(TAG,"First Visible item = ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total Count = ${layoutManager.itemCount}")
                if(totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5 ){
                    pageNub++
                    getNews()
                }


            }


        })
    }

    private fun getNews() {

        Log.d(TAG, "Request Done for ${pageNub}")
        val news: Call<News> = NewsServices.newsInstance.getHeadlines("in", pageNub)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                       totalResult = news.totalResults
//                    Log.d("MITESH", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("MITESH", "Error In Fetching News", t)
            }

        })
    }
}


//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=356800ee65934fd3b1b6e9b871d7388d