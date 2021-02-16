package com.dev.itunessearch.presentation.favorites

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.itunessearch.R
import com.dev.itunessearch.domain.model.ITunesEntity
import java.util.*

internal class FavoritesItemsAdapter(private val listener: OnRemoveClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<ITunesEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_item, parent, false)
        return FavoriteViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoriteViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): ITunesEntity {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<ITunesEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    class FavoriteViewHolder(
        private val listener: OnRemoveClickListener,
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.item_name)
        private val text: TextView = view.findViewById(R.id.item_text)
        private val photo: ImageView = view.findViewById(R.id.item_photo)
        private val removeBtn: ImageView = view.findViewById(R.id.action_button)
        private lateinit var item: ITunesEntity

        init {
            removeBtn.setOnClickListener {
                listener.onRemoveClick(item.id, adapterPosition)
            }
            removeBtn.visibility = View.VISIBLE
            removeBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        }

        fun onBind(item: ITunesEntity) {
            this.item = item
            name.text = item.artist
            text.text = item.name
            item.photoGrayscale?.let{
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                photo.setImageBitmap(bitmap)
            }
        }
    }
}
