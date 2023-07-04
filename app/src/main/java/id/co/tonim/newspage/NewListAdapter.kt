package id.co.tonim.newspage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.co.tonim.newspage.R // Ganti dengan nama package yang benar untuk file drawable

class NewsListAdapter(private val context: Context, private val newsList: List<News>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.newsImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.newsTitleTextView)

        fun bind(news: News) {
            titleTextView.text = news.title

            if (news.urlToImage.isNotEmpty()) {
                Picasso.get().load(news.urlToImage).into(imageView)
            } else {
                imageView.setImageResource(R.drawable.imagetest)
            }

            itemView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
}

