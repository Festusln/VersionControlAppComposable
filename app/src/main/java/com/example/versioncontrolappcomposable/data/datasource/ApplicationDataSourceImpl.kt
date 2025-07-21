package com.example.versioncontrolappcomposable.data.datasource

import com.example.versioncontrolappcomposable.data.api.ApiService
import com.example.versioncontrolappcomposable.data.models.RepoSearchResponseData
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.data.models.UserSearchResponse
import retrofit2.Response
import javax.inject.Inject

class ApplicationDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): ApplicationDataSource {
    override suspend fun searchGithubUser(username: String): Response<UserSearchResponse> {
        return apiService.searchGithubUser(username)
    }

    override suspend fun searchGithubRepo(keyword: String): Response<RepoSearchResponseData> {
        return apiService.searchGithubRepo(keyword)
    }

    override suspend fun fetchUserProfile(url: String): Response<User> {
        return apiService.fetchUserProfile(url)
    }

    override suspend fun fetchUserRepo(url: String): Response<Array<Repository>> {
        return apiService.fetchUserRepo(url)
    }

}