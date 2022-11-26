package com.yellowmessenger.assignment.dataSource

interface Repository {

    suspend fun getGithubUsers(searchString: String) : UserResponse

    suspend fun getFollowersList(followersUrl: String) : ArrayList<User>
}