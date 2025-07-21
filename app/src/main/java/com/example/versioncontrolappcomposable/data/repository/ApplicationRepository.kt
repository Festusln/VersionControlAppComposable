package com.example.versioncontrolappcomposable.data.repository

import com.example.utilities.ResourceState
import com.example.versioncontrolappcomposable.data.datasource.ApplicationDataSource
import com.example.versioncontrolappcomposable.data.models.RepoSearchResponseData
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.data.models.UserSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApplicationRepository @Inject constructor(private val applicationDataSource: ApplicationDataSource) {
    fun searchGithubUser(username: String) : Flow<ResourceState<UserSearchResponse>> {
        return flow {
            emit(ResourceState.Loading())

            val response = applicationDataSource.searchGithubUser(username)
            if (response.isSuccessful && response.body()!= null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error Fetching Data"))
            }
        } .catch {e ->
            emit(ResourceState.Error(e.localizedMessage ?:"Some error in flow"))
        }
    }


    fun searchGithubRepo(keyWord: String) : Flow<ResourceState<RepoSearchResponseData>> {
        return flow {
            emit(ResourceState.Loading())

            val response = applicationDataSource.searchGithubRepo(keyWord)
            if (response.isSuccessful && response.body()!= null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error Fetching Data"))
            }
        } .catch {e ->
            emit(ResourceState.Error(e.localizedMessage ?:"Some error in flow"))
        }
    }

    fun fetchUserProfile(url: String) : Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.Loading())

            val response = applicationDataSource.fetchUserProfile(url)
            if (response.isSuccessful && response.body()!= null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error Fetching Data"))
            }
        } .catch {e ->
            emit(ResourceState.Error(e.localizedMessage ?:"Some error in flow"))
        }
    }

    fun fetchUserRepo(url: String) : Flow<ResourceState<Array<Repository>>> {
        return flow {
            emit(ResourceState.Loading())

            val response = applicationDataSource.fetchUserRepo(url)
            if (response.isSuccessful && response.body()!= null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error Fetching Data"))
            }
        } .catch {e ->
            emit(ResourceState.Error(e.localizedMessage ?:"Some error in flow"))
        }
    }
}