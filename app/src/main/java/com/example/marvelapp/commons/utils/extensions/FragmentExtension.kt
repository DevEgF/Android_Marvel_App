package com.example.marvelapp.commons.utils.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(@StringRes textResId: Int) =
    Toast.makeText(requireContext(), textResId, Toast.LENGTH_LONG).show()
