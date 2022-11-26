package com.yellowmessenger.assignment.ui.recyclerview

import com.yellowmessenger.assignment.dataSource.User
import com.yellowmessenger.assignment.viewmodel.MainSharedViewModel

data class UserContainerViewModel(
    val type: Int = MainSharedViewModel.UserItem,
    val user: User
) : BaseRecyclerViewListItemViewModel(itemType = type) {

    override fun getRecyclerviewIdentifier(): Int = type.hashCode() + user.hashCode()
}