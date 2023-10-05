package com.dicodingSubmission.githubuser.api

import com.dicodingSubmission.githubuser.data.model.DetailUserResponse
import com.dicodingSubmission.githubuser.data.model.User
import com.dicodingSubmission.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username :String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}