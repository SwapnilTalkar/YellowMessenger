package com.yellowmessenger.assignment.di.component

import com.yellowmessenger.assignment.di.ActivityScope
import com.yellowmessenger.assignment.di.module.ActivityModule
import com.yellowmessenger.assignment.ui.screens.FollowersFragment
import com.yellowmessenger.assignment.ui.screens.UserFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        ActivityModule::class
    ]
)
interface ActivityComponent {

    fun inject(userFragment: UserFragment)

    fun inject(followersFragment: FollowersFragment)
}