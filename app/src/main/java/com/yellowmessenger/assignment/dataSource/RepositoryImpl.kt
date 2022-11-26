package com.yellowmessenger.assignment.dataSource

import com.yellowmessenger.assignment.dataSource.api.HomeApi
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : Repository {

    private val  dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getGithubUsers(searchString: String): UserResponse {
        return withContext(dispatcher) {
            homeApi.getUsers(searchString = searchString)
        }
    }

    override suspend fun getFollowersList(followersUrl: String): ArrayList<User> {
        return withContext(dispatcher) {
            homeApi.getFollowers(url = followersUrl)
        }
    }
}