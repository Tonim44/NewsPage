package id.co.tonim.newspage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.tonim.newspage.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsList: MutableList<News>
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = MySpanSizeLookup(5, 1, 2)
        recyclerView.layoutManager = layoutManager

        newsList = mutableListOf()
        newsAdapter = NewsAdapter(newsList)
        recyclerView.adapter = newsAdapter


        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform search as the user types (optional)
                performSearch(newText)
                return true
            }
        })

        fetchNewsData()

        binding.readnews.setOnClickListener {
            startActivity(Intent(this, ListReadNewsActivity::class.java))
            finish()
        }
    }

    private fun fetchNewsData() {
        val client = OkHttpClient()
        val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=84b8e7b897a949c4be8da6583a9d1007"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseData = response.body?.string()
                    responseData?.let {
                        val jsonObject = JSONObject(it)
                        val jsonArray = jsonObject.getJSONArray("articles")

                        for (i in 0 until jsonArray.length()) {
                            val newsObject = jsonArray.getJSONObject(i)

                            val source = newsObject.getJSONObject("source")
                            val name = source.getString("name")

                            val author = newsObject.getString("author")
                            val title = newsObject.getString("title")
                            val description = newsObject.getString("description")
                            val url = newsObject.getString("url")
                            val urlToImage = newsObject.getString("urlToImage")
                            val publishedAt = newsObject.getString("publishedAt")

                            val news = News(name, author, title, description, url,
                                urlToImage, publishedAt)
                            newsList.add(news)
                        }

                        runOnUiThread {
                            newsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredNewsList = newsList.filter { news ->
            news.title!!.contains(query, ignoreCase = true)
        }
        newsAdapter.setNewsList(filteredNewsList)
    }
}