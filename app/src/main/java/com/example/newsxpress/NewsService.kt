package com.example.newsxpress

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "356800ee65934fd3b1b6e9b871d7388d"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>


}
// https://newsapi.org/v2/top-headlines?apiKey=$API_KEY&country=in&page=1
//https://newsapi.org/v2/top-headlines?country=in&apiKey=356800ee65934fd3b1b6e9b871d7388d

object NewsServices {

    val newsInstance: NewsInterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)

    }


}
