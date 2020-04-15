package com.iqbal.databaseroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rec_item_appidea.view.*

class AppIdeaRecyclerViewAdapter internal constructor(
    context: Context,
    private val te: TouchEvent
) : RecyclerView.Adapter<AppIdeaRecyclerViewAdapter.AppIdeaViewHolder>() {

    interface TouchEvent {
        fun onClick(item: AppIdea)
        fun onHold(item: AppIdea)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var objects = emptyList<AppIdea>() // Cached copy of objects

    inner class AppIdeaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppIdeaViewHolder {
        val itemView = inflater.inflate(R.layout.rec_item_appidea, parent, false)
        return AppIdeaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppIdeaViewHolder, position: Int) {
        val current = objects[position]

        holder.itemView.text_title.text = current.title
        holder.itemView.text_lang.text = current.language

        holder.itemView.setOnClickListener {
            te.onClick(current)
        }
        holder.itemView.setOnLongClickListener {
            te.onHold(current)
            return@setOnLongClickListener true
        }
    }

    internal fun setItems(items: List<AppIdea>) {
        this.objects = items
        notifyDataSetChanged()
    }

    internal fun getItem(pos: Int): AppIdea {
        return this.objects[pos]
    }

    override fun getItemCount() = objects.size
}
