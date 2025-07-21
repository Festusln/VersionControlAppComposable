package com.example.versioncontrolappcomposable.data.datasource

import com.example.versioncontrolappcomposable.data.models.RepoSearchResponseData
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.data.models.UserSearchResponse
import retrofit2.Response

interface ApplicationDataSource {
    suspend fun searchGithubUser(username: String): Response<UserSearchResponse>
    suspend fun searchGithubRepo(keyword: String): Response<RepoSearchResponseData>
    suspend fun fetchUserProfile(url: String): Response<User>
    suspend fun fetchUserRepo(url: String):Response<Array<Repository>>
}