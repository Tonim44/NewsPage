package id.co.tonim.newspage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val name: String, val author: String?,
    val title: String?, val description: String?,
    val url: String?, val urlToImage: String,
    val publishAt: String?) : Parcelable

