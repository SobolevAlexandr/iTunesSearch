package com.dev.itunessearch.presentation.favorites

import android.media.MediaPlayer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.itunessearch.R
import com.dev.itunessearch.domain.model.Status
import com.dev.itunessearch.presentation.base.BaseFragment

class FavoritesFragment : BaseFragment<FavoritesItemsViewModel>(), OnRemoveClickListener {

    override val viewModel: FavoritesItemsViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: FavoritesItemsAdapter
    private lateinit var progressBar: View
    private lateinit var progressBarOverlay: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaPlayer: MediaPlayer

    @LayoutRes
    override fun getLayoutResId() = R.layout.fragment_favorites

    override fun initViews(view: View) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.fragment_favorites)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }

        progressBar = view.findViewById(R.id.progress_bar)
        progressBarOverlay = view.findViewById(R.id.progress_bar_overlay)
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = FavoritesItemsAdapter(this)
        recyclerView.adapter = adapter
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.remove_item_sound)
    }

    override fun subscribeUI() {
        viewModel.data.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    progressBarOverlay.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    progressBarOverlay.visibility = View.INVISIBLE
                    adapter.addData(it.data ?: emptyList())
                }
                Status.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    progressBarOverlay.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.general_error), Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.loadItems()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRemoveClick(id: Long, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_confirmation_text))
            .setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
                viewModel.removeItem(id)
                adapter.removeItem(position)
                mediaPlayer.start()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()

    }

    companion object {
        const val FRAGMENT_NAME = "favorites_search"
    }
}