package com.dev.itunessearch.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.itunessearch.R
import com.dev.itunessearch.domain.model.ITunesItemWrapper
import com.squareup.picasso.Picasso
import java.util.*

internal class SearchItemsAdapter(private val listener: OnFavoritesClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<ITunesItemWrapper> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_item, parent, false)
        return SearchViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): ITunesItemWrapper {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<ITunesItemWrapper>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class SearchViewHolder(
        private val listener: OnFavoritesClickListener,
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.item_name)
        private val text: TextView = view.findViewById(R.id.item_text)
        private val photo: ImageView = view.findViewById(R.id.item_photo)
        private val addToFavoritesBtn: ImageView = view.findViewById(R.id.action_button)
        private lateinit var item: ITunesItemWrapper

        init {
            addToFavoritesBtn.setOnClickListener {
                val isFavorite = item.isFavorite
                if (isFavorite) listener.removeFromFavorites(item.item)
                else listener.addToFavorites(photo.drawable, item.item)
                item.isFavorite = !isFavorite
                updateAddToFavoritesButton()
            }
            addToFavoritesBtn.visibility = View.VISIBLE
        }

        fun onBind(item: ITunesItemWrapper) {
            this.item = item
            name.text = item.item.artist
            text.text = item.item.getName()
            updateAddToFavoritesButton()
            Picasso.get().load(item.item.photoUrl).into(photo)
        }

        private fun updateAddToFavoritesButton() {
            addToFavoritesBtn.setImageResource(
                if (item.isFavorite) android.R.drawable.star_on
                else android.R.drawable.star_off
            )
        }
    }
}
