package ru.ohmy.theme.presentation.common.android.adapter


import android.support.v7.widget.RecyclerView
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.core.ext.runIf

@Suppress("unused", "MemberVisibilityCanPrivate", "MemberVisibilityCanBePrivate")
abstract class BaseRecyclerAdapter<Model, ViewHolder : RecyclerView.ViewHolder>(
        hasStableIds: Boolean = false
) : RecyclerView.Adapter<ViewHolder>() {

    val log by logger()

    private val items = mutableListOf<Model>()

    init {
        setHasStableIds(hasStableIds)
    }

    final override fun setHasStableIds(hasStableIds: Boolean) = super.setHasStableIds(hasStableIds)

    override fun getItemCount() = items.size

    fun getItem(position: Int) = items[position]

    fun addItems(newItems: List<Model>, notify: Boolean) {
        if (newItems.isNotEmpty()) items.addAll(newItems)
        if (notify) notifyDataSetChanged()
    }

    fun replaceItems(items: List<Model>, notify: Boolean) {
        clear()
        addItems(items, notify)
    }

    fun removeItem(position: Int, notify: Boolean) = items.removeAt(position).also {
        if (notify) notifyItemRemoved(position)
    }

    fun removeItem(item: Model, notify: Boolean): Model? = items.indexOf(item).let {
        runIf(it > -1) { removeItem(it, notify) }
    }

    fun clear() = items.clear()

}

