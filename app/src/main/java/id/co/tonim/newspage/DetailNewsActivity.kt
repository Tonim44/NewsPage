package id.co.tonim.newspage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.co.tonim.newspage.databinding.ActivityDetailNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var news: News
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val EXTRA_NEWS = "extra_news"
        const val NEWS_PREFERENCES = "news_preferences"
        const val NEWS_KEY_PREFIX = "news_"
    }

    private lateinit var binding: ActivityDetailNewsBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        news = intent.getParcelableExtra<News>(EXTRA_NEWS) as News

        val name = news.name
        val author = news.author
        val title = news.title
        val description = news.description
        val url = news.url
        val urlToImage = news.urlToImage
        val publishAt = news.publishAt

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })

        binding.detailAuthorTextView.text = author
        binding.detailTitleTextView.text = title
        binding.detailDescriptionTextView.text = description
        binding.detailNameTextView.text = name

        if (urlToImage.isNotEmpty()) {
            Picasso.get().load(urlToImage).into(binding.imageView)
        } else {
            binding.imageView.setImageResource(R.drawable.imagetest)
        }

        binding.openlink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        val formattedPublishAt = convertPublishedTime(publishAt)
        binding.detailpublishAtTextView.text = formattedPublishAt

        sharedPreferences = getSharedPreferences(NEWS_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val newsSet = sharedPreferences.getStringSet(NEWS_KEY_PREFIX + "set", mutableSetOf())?.toMutableSet()
        newsSet?.add(name)

        editor.putStringSet(NEWS_KEY_PREFIX + "set", newsSet)
        editor.putString(NEWS_KEY_PREFIX + name + "_author", author)
        editor.putString(NEWS_KEY_PREFIX + name + "_title", title)
        editor.putString(NEWS_KEY_PREFIX + name + "_description", description)
        editor.putString(NEWS_KEY_PREFIX + name + "_url", url)
        editor.putString(NEWS_KEY_PREFIX + name + "_urlToImage", urlToImage)
        editor.putString(NEWS_KEY_PREFIX + name + "_publishAt", formattedPublishAt)
        editor.apply()
    }

    private fun convertPublishedTime(publishedAt: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("E, dd MMMM yyyy HH.mm", Locale.getDefault())
        val date = inputFormat.parse(publishedAt)
        return outputFormat.format(date)
    }
}