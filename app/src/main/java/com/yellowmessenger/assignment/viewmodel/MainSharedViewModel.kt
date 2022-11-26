package com.yellowmessenger.assignment.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yellowmessenger.assignment.R
import com.yellowmessenger.assignment.dataSource.Repository
import com.yellowmessenger.assignment.dataSource.User
import com.yellowmessenger.assignment.ui.recyclerview.BaseRecyclerViewListItemViewModel
import com.yellowmessenger.assignment.ui.recyclerview.UserContainerViewModel
import kotlinx.coroutines.launch

class MainSharedViewModel(
    private val repository: Repository
) : ViewModel() {

    companion object {
        const val UserItem = 1
        const val FollowerUrl = "FollowerUrl"
    }
    val loading: LiveData<Boolean> = MutableLiveData(false)
    val users = ObservableArrayList<BaseRecyclerViewListItemViewModel>()
    val followers = ObservableArrayList<BaseRecyclerViewListItemViewModel>()

    val action: LiveData<Action> = MutableLiveData()

    fun makePrimaryApiCall(searchedText: String) {
        (loading as MutableLiveData).value = true
        viewModelScope.launch {
            try {
                users.clear()
                val response = repository.getGithubUsers(
                    searchString = searchedText
                )
                parseResponse(response.userList, users)
                loading.value = false
            } catch (e: Exception) {
                loading.value = false
                (action as MutableLiveData).value = Action.ShowError(R.string.exception)
            }
        }
    }

    fun makeSecondaryApiCall(followersUrl: String) {
        (loading as MutableLiveData).value = true
        viewModelScope.launch {
            try {
                followers.clear()
                val response = repository.getFollowersList(
                    followersUrl = followersUrl
                )

                loading.value = false
                parseResponse(response, followers)
            } catch (e: Exception) {
                loading.value = false
                (action as MutableLiveData).value = Action.ShowError(R.string.exception)
            }
        }
    }

    private fun parseResponse(
        userList: List<User>?,
        users: ObservableArrayList<BaseRecyclerViewListItemViewModel>
    ) {

        if (userList.isNullOrEmpty()) {
            (action as MutableLiveData).value = Action.ShowError(R.string.empty_user_list)
            return
        }

        userList.map {
            UserContainerViewModel(
                user = it
            )
        }.let {
            users.clear()
            users.addAll(it)
        }
    }

    sealed class Action {
        data class ShowError(val errorResourceId: Int) : Action()
    }
}