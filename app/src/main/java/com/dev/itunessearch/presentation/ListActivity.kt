package com.dev.itunessearch.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dev.itunessearch.presentation.search.SearchFragment
import com.android.itunessearch.R
import com.dev.itunessearch.presentation.favorites.FavoritesFragment

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        if (savedInstanceState == null) {
            navigateToSearchPage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_list_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_show_favorites) {
            navigateToFavoritesPage()
            return true
        }
        return false
    }

    private fun navigateToSearchPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                SearchFragment(),
                SearchFragment.FRAGMENT_NAME
            )
            .addToBackStack(SearchFragment.FRAGMENT_NAME)
            .commit()
    }


    private fun navigateToFavoritesPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                FavoritesFragment(),
                FavoritesFragment.FRAGMENT_NAME
            )
            .addToBackStack(FavoritesFragment.FRAGMENT_NAME)
            .commit()
    }
}
