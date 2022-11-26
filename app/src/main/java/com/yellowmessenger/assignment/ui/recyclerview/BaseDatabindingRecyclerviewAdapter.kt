package com.yellowmessenger.assignment.ui.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yellowmessenger.assignment.BR

open class BaseDataBindingRecyclerviewAdapter(
    private var bindingLayoutResId: Int,
    private var callback: Any? = null,
    private val shouldExecutePendingBindings: Boolean = false
) : RecyclerView.Adapter<ViewDataBindingHolder>() {

    protected var list: ObservableList<*>? = null
    var listBound: Boolean = false

    init {
        setHasStableIds(true)
    }

    // region Observable List
    open fun <AnyClass : BaseRecyclerViewListItemViewModel> bindObservableList(
        list: ObservableList<AnyClass>,
        ignoreNullCheck: Boolean = false
    ) {

        if (this.list != list || ignoreNullCheck) {
            this.list = list
            this.listBound = true
            notifyDataSetChanged()

            object : ObservableList.OnListChangedCallback<ObservableList<AnyClass>>() {
                override fun onChanged(sender: ObservableList<AnyClass>?) =
                    onListChanged()

                override fun onItemRangeRemoved(
                    sender: ObservableList<AnyClass>?,
                    positionStart: Int,
                    itemCount: Int
                ) = onListItemRangeRemoved(positionStart, itemCount)

                override fun onItemRangeMoved(
                    sender: ObservableList<AnyClass>?,
                    fromPosition: Int,
                    toPosition: Int,
                    itemCount: Int
                ) = onListItemRangeMoved(fromPosition, toPosition, itemCount)

                override fun onItemRangeInserted(
                    sender: ObservableList<AnyClass>?,
                    positionStart: Int,
                    itemCount: Int
                ) = onListItemRangeInserted(positionStart, itemCount)

                override fun onItemRangeChanged(
                    sender: ObservableList<AnyClass>?,
                    positionStart: Int,
                    itemCount: Int
                ) = onListItemRangeChanged(positionStart, itemCount)
            }.run {
                list.addOnListChangedCallback(this)
            }
        }
    }
    // endregion Observable List

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    open fun getItem(position: Int): Any? {
        return list?.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        item?.let {
            return (it as BaseRecyclerViewListItemViewModel).getRecyclerviewIdentifier().toLong()
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataBindingHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            bindingLayoutResId,
            parent,
            false
        )
        callback?.let {
            binding?.setVariable(BR.callback, callback)
        }
        return ViewDataBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewDataBindingHolder, position: Int) {
        getItem(position)?.let {
            (it as BaseRecyclerViewListItemViewModel).setItemPosition(position)
            holder.binding.setVariable(BR.viewModel, it)
        }
        if (shouldExecutePendingBindings) {
            holder.binding.executePendingBindings()
        }
    }

    // region List Change Callbacks
    /**
     * Called whenever a change of unknown type has occurred, such as the entire list being
     * set to new values.
     */
    open fun onListChanged() {
        Log.d(
            this.javaClass.simpleName,
            "onChanged"
        )
        notifyDataSetChanged()
    }

    /**
     * Called whenever one or more items in the list have changed.
     * @param positionStart The starting index that has changed.
     * @param itemCount The number of items that have changed.
     */
    open fun onListItemRangeChanged(positionStart: Int, itemCount: Int) {
        Log.d(
            this.javaClass.simpleName,
            "onItemRangeChanged start: $positionStart count: $itemCount"
        )
        notifyItemRangeChanged(positionStart, itemCount)
    }

    /**
     * Called whenever items have been inserted into the list.
     * @param positionStart The insertion index
     * @param itemCount The number of items that have been inserted
     */
    open fun onListItemRangeInserted(positionStart: Int, itemCount: Int) {
        Log.d(
            this.javaClass.simpleName,
            "onItemRangeInserted start: $positionStart count: $itemCount"
        )
        Runnable {
            notifyItemRangeInserted(positionStart, itemCount)
        }.run()
    }

    /**
     * Called whenever items in the list have been moved.
     * @param fromPosition The position from which the items were moved
     * @param toPosition The destination position of the items
     * @param itemCount The number of items moved
     */
    open fun onListItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        Log.d(
            this.javaClass.simpleName,
            "onItemRangeMoved from: $fromPosition to: $toPosition itemCount: $itemCount"
        )
        notifyDataSetChanged()
    }

    /**
     * Called whenever items in the list have been deleted.
     * @param positionStart The starting index of the deleted items.
     * @param itemCount The number of items removed.
     */
    open fun onListItemRangeRemoved(positionStart: Int, itemCount: Int) {
        Log.d(
            this.javaClass.simpleName,
            "onItemRangeRemoved start: $positionStart count: $itemCount"
        )
        notifyItemRangeRemoved(positionStart, itemCount)
    }

}
// endregion List Change Callbacks
