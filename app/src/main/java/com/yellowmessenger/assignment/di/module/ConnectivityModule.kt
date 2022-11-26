package com.yellowmessenger.assignment.di.module

import android.content.Context
import com.yellowmessenger.assignment.utils.ConnectivityListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConnectivityModule {

    @Singleton
    @Provides
    fun getNetworkListener(context: Context): ConnectivityListener {
        return ConnectivityListener(context)
    }
}