package com.nmt.film4life.listener

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(var linearLayoutManager: LinearLayoutManager):RecyclerView.OnScrollListener(){

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItems=linearLayoutManager.childCount
        val totalItem=linearLayoutManager.itemCount
        val firstVisibleItem=linearLayoutManager.findFirstVisibleItemPosition()
        if(!isLoading() && !isLastPage())
        {
            if((visibleItems+firstVisibleItem)>=totalItem && firstVisibleItem>0){
                loadMore()
            }
        }
    }
    protected abstract fun loadMore()
    abstract fun isLastPage() : Boolean
    abstract fun isLoading() : Boolean
}