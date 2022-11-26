package com.yellowmessenger.assignment.di.component

import android.content.Context
import com.yellowmessenger.assignment.di.module.ConnectivityModule
import com.yellowmessenger.assignment.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ConnectivityModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    fun getActivityComponent() : ActivityComponent

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): ApplicationComponent
    }

}