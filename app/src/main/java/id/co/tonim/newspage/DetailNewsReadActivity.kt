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
import java.util.Locale

class DetailNewsReadActivity : AppCompatActivity() {

    private lateinit var news: News

    companion object {
        const val EXTRA_NEWS = "extra_news"
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
        binding.detailpublishAtTextView.text = publishAt
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

    }
}