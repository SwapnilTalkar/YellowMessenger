package com.yellowmessenger.assignment

import android.app.Application
import com.yellowmessenger.assignment.di.component.ApplicationComponent
import com.yellowmessenger.assignment.di.component.DaggerApplicationComponent

class YellowMessengerApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}