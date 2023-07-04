package id.co.tonim.newspage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(private var newsList: List<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = newsList[position]
        holder.authorTextView.text = currentNews.author
        holder.titleTextView.text = currentNews.title

        if (currentNews.urlToImage.isNotEmpty()) {
            Picasso.get().load(currentNews.urlToImage).into(holder.imageView)
        }
        if (currentNews.urlToImage.equals(null)) {
            holder.imageView.setImageResource(R.drawable.imagetest)
        }
        else {
            // Set a placeholder image if the URL is empty
            holder.imageView.setImageResource(R.drawable.imagetest)
        }

        // Set click listener for the item view
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_NEWS, currentNews)
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsList(list: List<News>) {
        newsList = list
        notifyDataSetChanged()
    }
}