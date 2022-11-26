package com.yellowmessenger.assignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityListener(private val context: Context) {

    fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}