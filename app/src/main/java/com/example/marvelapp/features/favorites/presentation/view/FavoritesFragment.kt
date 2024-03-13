package com.example.marvelapp.features.favorites.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marvelapp.commons.presentation.adapter.getGenericAdapterOf
import com.example.marvelapp.databinding.FragmentFavoritesBinding
import com.example.marvelapp.features.favorites.presentation.viewholder.FavoriteViewHolder
import com.example.marvelapp.features.favorites.presentation.viewmodel.FavoritesViewModel
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val favoriteAdapter by lazy {
        getGenericAdapterOf {
            FavoriteViewHolder.create(it, imageLoader)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavoritesBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteAdapter()
        observeFavoriteState()
    }

    private fun initFavoriteAdapter(){
        binding.recyclerFavorites.run {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavoriteState() {
        viewModel.state.observe(viewLifecycleOwner){ uiState ->
            binding.flipperFavorite.displayedChild = when(uiState) {
                is FavoritesViewModel.UiState.ShowFavorite -> {
                    favoriteAdapter.submitList(uiState.favorite)
                    FLIPPER_CHILD_CHARACTER
                }
                FavoritesViewModel.UiState.ShowEmpty -> {
                    favoriteAdapter.submitList(emptyList())
                    FLIPPER_CHILD_EMPTY
                }
            }
        }
        viewModel.getAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_CHARACTER = 0
        private const val FLIPPER_CHILD_EMPTY = 1
    }
}