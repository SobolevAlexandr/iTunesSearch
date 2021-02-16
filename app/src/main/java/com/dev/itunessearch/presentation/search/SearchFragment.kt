package com.dev.itunessearch.presentation.search

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.itunessearch.R
import com.dev.itunessearch.domain.model.ITunesItem
import com.dev.itunessearch.domain.model.Status
import com.dev.itunessearch.presentation.base.BaseFragment
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchItemsViewModel>(), OnFavoritesClickListener, View.OnClickListener {

    override val viewModel: SearchItemsViewModel by viewModels { viewModelFactory }
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: SearchItemsAdapter
    private lateinit var progressBar: View
    private lateinit var progressBarOverlay: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var filterButtonAll: CheckedTextView
    private lateinit var filterButtonMusic: CheckedTextView
    private lateinit var filterButtonMovie: CheckedTextView
    private lateinit var filterButtonSoftware: CheckedTextView
    private var filterMode = SearchItemsViewModel.FilterMode.ALL
    private val handler = Handler(Looper.getMainLooper())
    private var searchCallback = Runnable {
        viewModel.loadItems(filterMode, searchView.query.toString())
    }

    @LayoutRes
    override fun getLayoutResId() = R.layout.fragment_search

    override fun initViews(view: View) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.fragment_search)
        }

        progressBar = view.findViewById(R.id.progress_bar)
        progressBarOverlay = view.findViewById(R.id.progress_bar_overlay)
        recyclerView = view.findViewById(R.id.recycler_view)
        searchView = view.findViewById(R.id.search_view)
        filterButtonAll = view.findViewById(R.id.btn_filter_all)
        filterButtonMusic = view.findViewById(R.id.btn_filter_music)
        filterButtonMovie = view.findViewById(R.id.btn_filter_movie)
        filterButtonSoftware = view.findViewById(R.id.btn_filter_software)

        adapter = SearchItemsAdapter(this)
        recyclerView.adapter = adapter
        searchView.setQuery(sharedPreferences.getString(PREFS_KEY_LAST_QUERY, null), false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacks(searchCallback)
                handler.postDelayed(searchCallback, SEARCH_DELAY)
                return false
            }
        })
        filterButtonAll.setOnClickListener(this)
        filterButtonMusic.setOnClickListener(this)
        filterButtonMovie.setOnClickListener(this)
        filterButtonSoftware.setOnClickListener(this)
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
        viewModel.loadItems(filterMode, searchView.query.toString())
    }

    override fun addToFavorites(drawable: Drawable?, item: ITunesItem) {
        viewModel.addToFavorites(drawable, item)
    }

    override fun removeFromFavorites(item: ITunesItem) {
        viewModel.removeFromFavorites(item)
    }

    override fun onClick(view: View?) {
        val viewId = view?.id ?: return
        when (viewId) {
            R.id.btn_filter_all -> {
                filterButtonAll.isChecked = true
                filterButtonMusic.isChecked = false
                filterButtonMovie.isChecked = false
                filterButtonSoftware.isChecked = false
                filterMode = SearchItemsViewModel.FilterMode.ALL
            }
            R.id.btn_filter_music -> {
                filterButtonAll.isChecked = false
                filterButtonMusic.isChecked = true
                filterButtonMovie.isChecked = false
                filterButtonSoftware.isChecked = false
                filterMode = SearchItemsViewModel.FilterMode.MUSIC
            }
            R.id.btn_filter_movie -> {
                filterButtonAll.isChecked = false
                filterButtonMusic.isChecked = false
                filterButtonMovie.isChecked = true
                filterButtonSoftware.isChecked = false
                filterMode = SearchItemsViewModel.FilterMode.MOVIE
            }
            R.id.btn_filter_software -> {
                filterButtonAll.isChecked = false
                filterButtonMusic.isChecked = false
                filterButtonMovie.isChecked = false
                filterButtonSoftware.isChecked = true
                filterMode = SearchItemsViewModel.FilterMode.SOFTWARE
            }
        }
        viewModel.loadItems(filterMode, searchView.query.toString())
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences
            .edit()
            .putString(PREFS_KEY_LAST_QUERY, searchView.query.toString())
            .apply()
    }

    companion object {
        const val FRAGMENT_NAME = "fragment_search"
        private const val SEARCH_DELAY = 500L
        private const val PREFS_KEY_LAST_QUERY = "prefs_key_last_query"
    }
}