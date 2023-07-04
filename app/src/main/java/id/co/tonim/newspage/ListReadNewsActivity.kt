package id.co.tonim.newspage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.tonim.newspage.databinding.ActivityReadNewsBinding

class ListReadNewsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var newsListView: RecyclerView

    companion object {
        const val NEWS_PREFERENCES = "news_preferences"
        const val NEWS_KEY_PREFIX = "news_"
    }

    private lateinit var binding: ActivityReadNewsBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityReadNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(NEWS_PREFERENCES, MODE_PRIVATE)

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })
        val recyclerView: RecyclerView = binding.newsListView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val newsSet = sharedPreferences.getStringSet(NEWS_KEY_PREFIX + "set", mutableSetOf())
        val newsList = newsSet?.mapNotNull { name ->

            val author = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_author", null)
            val url = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_url", null)
            val publishAt = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_publishAt",null)
            val urlToImage = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_urlToImage", null)
            val title = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_title", null)
            val description = sharedPreferences.getString(NEWS_KEY_PREFIX + name + "_description", null)

            urlToImage?.let {
                News(name, author, title, description, url, urlToImage, publishAt)
            }
        }

        val adapter = NewsListAdapter(this@ListReadNewsActivity, newsList ?: emptyList())
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { position ->
            val selectedNews = newsList!![position]
            val intent = Intent(this@ListReadNewsActivity, DetailNewsReadActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_NEWS, selectedNews)
            startActivity(intent)
        }
    }
}
