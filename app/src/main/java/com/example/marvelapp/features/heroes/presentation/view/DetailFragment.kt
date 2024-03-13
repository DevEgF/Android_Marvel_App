package com.example.marvelapp.features.heroes.presentation.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.extensions.showShortToast
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.features.heroes.presentation.adapter.DetailParentAdapter
import com.example.marvelapp.features.heroes.presentation.livedata.FavoriteUiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.livedata.UiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.viewArgs.DetailViewArg
import com.example.marvelapp.features.heroes.presentation.viewmodel.DetailViewModel
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArg

        setupView(detailViewArg)
        setSharedElementTransitionOnEnter()
        loadCategoriesUiState(detailViewArg)
        setAndObserveFavoriteUiState(detailViewArg)
    }

    private fun setupView(args: DetailViewArg) {
        binding.charactersDescriptions.text = args.description
        binding.imageCharacters.run {
            transitionName = args.name
            imageLoader.load(
                this,
                args.imageUrl
            )
        }
    }

    private fun loadCategoriesUiState(args: DetailViewArg) {
        viewModel.categories.load(args.characterId)

        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when(uiState) {
                UiActionStateLiveData.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                is UiActionStateLiveData.UiState.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(
                            uiState.detailParentList,
                            imageLoader
                        )
                    }
                    FLIPPER_CHILD_POSITION_SUCCESS
                }
                UiActionStateLiveData.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(args.characterId)
                    }
                    FLIPPER_CHILD_POSITION_ERROR
                }
                UiActionStateLiveData.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
            }
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
        }
    }

    private fun setAndObserveFavoriteUiState(detailViewArg: DetailViewArg) {
        viewModel.favorite.run {
            checkFavorite(detailViewArg.characterId)

            binding.imageFavoriteIcon.setOnClickListener {
                update(detailViewArg)
            }

            state.observe(viewLifecycleOwner) {favoriteUiState ->
                binding.flipperFavorite.displayedChild = when(favoriteUiState) {
                    FavoriteUiActionStateLiveData.UiState.Loading -> {
                        FLIPPER_FAVORITE_CHILD_LOADING
                    }
                    is FavoriteUiActionStateLiveData.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(favoriteUiState.icon)
                        FLIPPER_FAVORITE_CHILD_IMAGE
                    }
                    is FavoriteUiActionStateLiveData.UiState.Error -> {
                        showShortToast(favoriteUiState.messageResId)
                        FLIPPER_FAVORITE_CHILD_IMAGE
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_SUCCESS = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
        private const val FLIPPER_FAVORITE_CHILD_LOADING = 1
        private const val FLIPPER_FAVORITE_CHILD_IMAGE = 0
    }
}