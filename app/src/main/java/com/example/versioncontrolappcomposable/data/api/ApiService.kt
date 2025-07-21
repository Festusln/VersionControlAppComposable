package com.example.versioncontrolappcomposable.data.api

import com.example.versioncontrolappcomposable.data.models.RepoSearchResponseData
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.data.models.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("search/users") // GitHub API endpoint
    suspend fun searchGithubUser(
        @Query("q") username: String
    ): Response<UserSearchResponse>


    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("search/repositories")
    suspend fun searchGithubRepo(
        @Query("q") keyword: String
    ): Response<RepoSearchResponseData>


    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET
    suspend fun fetchUserProfile(
        @Url url: String
    ): Response<User>


    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET
    suspend fun fetchUserRepo(
        @Url url: String
    ): Response<Array<Repository>>

}
