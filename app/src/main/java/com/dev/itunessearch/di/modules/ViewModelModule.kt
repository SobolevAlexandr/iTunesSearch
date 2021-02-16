package com.dev.itunessearch.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.itunessearch.presentation.utils.ViewModelFactory
import com.dev.itunessearch.di.utils.ViewModelKey
import com.dev.itunessearch.presentation.favorites.FavoritesItemsViewModel
import com.dev.itunessearch.presentation.search.SearchItemsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchItemsViewModel::class)
    internal abstract fun searchItemsViewModel(viewModel: SearchItemsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesItemsViewModel::class)
    internal abstract fun favoritesItemsViewModel(viewModel: FavoritesItemsViewModel): ViewModel
}