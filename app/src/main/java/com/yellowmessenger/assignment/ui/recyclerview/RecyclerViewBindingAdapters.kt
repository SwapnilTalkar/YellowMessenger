package com.yellowmessenger.assignment.ui.recyclerview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["list", "animateChanges"], requireAll = false)
    fun <T : BaseRecyclerViewListItemViewModel> setRecyclerViewData(
        recyclerView: RecyclerView,
        data: ObservableList<T>,
        animateChanges: Boolean? = false
    ) {
        recyclerView.adapter?.let {
            if (it is BaseDataBindingRecyclerviewAdapter) {
                if (!it.listBound) {
                    it.bindObservableList(data)
                    if (animateChanges != true) {
                        recyclerView.itemAnimator = null
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
}