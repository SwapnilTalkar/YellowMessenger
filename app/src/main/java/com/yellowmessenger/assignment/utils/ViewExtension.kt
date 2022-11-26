package com.yellowmessenger.assignment.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, message: String) {
    Snackbar.make(
        view.rootView,
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}