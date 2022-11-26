package com.yellowmessenger.assignment.dataSource.api

import com.yellowmessenger.assignment.dataSource.User
import com.yellowmessenger.assignment.dataSource.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface HomeApi {

    @GET("search/users")
    suspend fun getUsers(
        @Query("q") searchString: String
    ): UserResponse

    @GET()
    suspend fun getFollowers(
        @Url url: String
    ): ArrayList<User>

}