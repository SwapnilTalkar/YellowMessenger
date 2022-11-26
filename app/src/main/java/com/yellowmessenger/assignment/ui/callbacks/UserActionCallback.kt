package com.yellowmessenger.assignment.ui.callbacks

import com.yellowmessenger.assignment.dataSource.User

interface UserActionCallback {

    fun onSearched()

    fun onUserSelected(user: User)
}