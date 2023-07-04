package id.co.tonim.newspage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MySpanSizeLookup(private val spanPos: Int, private val spanCount1: Int, private val spanCount2: Int) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (position % spanPos == 0) {
            spanCount2
        } else {
            spanCount1
        }
    }
}
