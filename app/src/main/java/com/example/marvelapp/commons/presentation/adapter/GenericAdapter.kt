package com.example.marvelapp.commons.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.marvelapp.commons.presentation.diffcallback.GenericDiffCallback
import com.example.marvelapp.commons.presentation.diffcallback.ListItem
import com.example.marvelapp.commons.presentation.viewholder.GenericViewHolder

inline fun <T: ListItem, VH: GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH
):ListAdapter<T, VH> {

    val diffCallback = GenericDiffCallback<T>()

    return object : ListAdapter<T, VH>(diffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }
    }
}