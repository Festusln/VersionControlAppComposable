package com.example.versioncontrolappcomposable.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilities.ResourceState
import com.example.versioncontrolappcomposable.data.datasource.ApplicationDataSource
import com.example.versioncontrolappcomposable.data.models.RepoSearchResponseData
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.data.models.UserSearchResponse
import com.example.versioncontrolappcomposable.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import com.example.versioncontrolappcomposable.data.repository.ApplicationRepository
import com.example.versioncontrolappcomposable.data.screenStateAndEvents.UserAndRepoSearchScreenState
import com.example.versioncontrolappcomposable.data.screenStateAndEvents.UserAndRepoSearchUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val dataSource: ApplicationDataSource
) : ViewModel() {

    private var uiState = mutableStateOf(UserAndRepoSearchScreenState())

    private val _navigateTo = MutableSharedFlow<String>() // the route name
    val navigateTo: SharedFlow<String> = _navigateTo


    private val _userSearch : MutableStateFlow<ResourceState<UserSearchResponse>> = MutableStateFlow(
        ResourceState.Loading())
    val userSearch : StateFlow<ResourceState<UserSearchResponse>> = _userSearch

    private val _repoSearch : MutableStateFlow<ResourceState<RepoSearchResponseData>> = MutableStateFlow(
        ResourceState.Loading())
    val repoSearch : StateFlow<ResourceState<RepoSearchResponseData>> = _repoSearch

    fun handleTransactionClick(fragmentTypeString: String) {
        when (fragmentTypeString) {
            "Users" -> viewModelScope.launch { _navigateTo.emit(Routes.USER_SCREEN) }
            "Repositories" -> viewModelScope.launch { _navigateTo.emit(Routes.REPO_SCREEN) }
            "Home" -> viewModelScope.launch { _navigateTo.emit(Routes.HOME_SCREEN) }
        }
    }

    fun onEvent(event : UserAndRepoSearchUiEvents) {
        when (event) {
            is UserAndRepoSearchUiEvents.UsernameEntered -> {
                uiState.value = uiState.value.copy(
                    userEntered = event.user
                )

                viewModelScope.launch {
                    _navigateTo.emit("${Routes.SEARCH_USER_SCREEN}/${event.user}")
                }


            }

            is UserAndRepoSearchUiEvents.RepoNameEntered -> {
                uiState.value = uiState.value.copy(
                    repoEntered = event.repoName
                )

                viewModelScope.launch {
                    _navigateTo.emit("${Routes.SEARCH_REPO_SCREEN}/${event.repoName}")
                }

            }

        }
    }

    fun searchGithubUser(user : String) {
        viewModelScope.launch (Dispatchers.IO) {
            applicationRepository.searchGithubUser(user)
                .collectLatest { userSearchResponse ->
                    _userSearch.value = userSearchResponse
                }
        }
    }

    fun searchGithubRepo(repo : String) {
        viewModelScope.launch (Dispatchers.IO) {
            applicationRepository.searchGithubRepo(repo)
                .collectLatest { repoSearchResponse ->
                    _repoSearch.value = repoSearchResponse
                }
        }
    }


    suspend fun fetchUserProfileOnce(url: String): ResourceState<User> {
        return try {
            val response = dataSource.fetchUserProfile(url)
            if (response.isSuccessful && response.body() != null) {
                ResourceState.Success(response.body()!!)
            } else {
                ResourceState.Error("Fetch failed: ${response.code()}")
            }
        } catch (e: Exception) {
            ResourceState.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun fetchUserRepo(url: String): ResourceState<Array<Repository>> {
        return try {
            val response = dataSource.fetchUserRepo(url)
            if (response.isSuccessful && response.body() != null) {
                ResourceState.Success(response.body()!!)
            } else {
                ResourceState.Error("Fetch failed: ${response.code()}")
            }
        } catch (e: Exception) {
            ResourceState.Error(e.localizedMessage ?: "Unknown error")
        }
    }




}