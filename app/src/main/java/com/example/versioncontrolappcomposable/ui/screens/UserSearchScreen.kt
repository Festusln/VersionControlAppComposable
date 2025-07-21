package com.example.versioncontrolappcomposable.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.utilities.CoreUtils
import com.example.utilities.ResourceState
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.ui.components.FixedTextFieldWithScrollableListForUsers
import com.example.versioncontrolappcomposable.ui.components.LoadingOverlay
import com.example.versioncontrolappcomposable.ui.components.UserProfileAndRepoComponent
import com.example.versioncontrolappcomposable.ui.navigation.Routes
import com.example.versioncontrolappcomposable.ui.viewmodel.TransactionViewModel
import androidx.compose.runtime.*
import com.example.versioncontrolappcomposable.data.models.Repository

@Composable
fun UserSearchScreen(navController: NavController,
                     user : String,
                     viewModel: TransactionViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var repos by remember { mutableStateOf<Array<Repository>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    if (!CoreUtils.isInternetAvailable(context)) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    LoadingOverlay("Searching user: $user")



    val userSearchResponse by viewModel.userSearch.collectAsState()
    LaunchedEffect(user) {
        viewModel.searchGithubUser(user)
    }

    when (userSearchResponse) {
        is ResourceState.Loading -> {
            println("Loading")
        }

        is ResourceState.Success -> {
            val response = (userSearchResponse as ResourceState.Success).data
            println("User search Success: $response")
            if (response.items.isEmpty()) {
                Routes.SEARCH_INFO = "We’ve searched the ends of the earth and we’ve not\nfound this user, please try again"
                navController.navigate(Routes.USER_SCREEN)
            } else {
                val userList = remember {mutableStateListOf<User>()}

                LaunchedEffect(Unit) {
                    for (item in response.items) {
                        val result = viewModel.fetchUserProfileOnce(item.url)

                        when (result) {

                            is ResourceState.Loading -> {
                                println("User search loading")
                            }

                            is ResourceState.Success -> {
                                val userResponse = result.data
                                println("Each user response: $userResponse")
                                userList.add(userResponse)
                            }

                            is ResourceState.Error -> {
                                val error = result.data
                                println("User search error: $error")
                                if (userList.isNotEmpty()) {
                                    println("user list not empty")
                                    break
                                } else {
                                    println("user list empty")
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                                    navController.popBackStack()
                                    break
                                }

                            }

                        }
                    }
                }

                FixedTextFieldWithScrollableListForUsers(
                    items = userList,
                    onUserClicked = {selectedUser = it},
                    onSearchButtonClicked = {navController.navigate("${Routes.SEARCH_USER_SCREEN}/$it")})

            }
        }

        is ResourceState.Error -> {
            val error = (userSearchResponse as ResourceState.Error).data
            println("User search Error: $error")
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//            navController.popBackStack()
        }


    }

    //This is triggered when a user is selected on the screen
    selectedUser?.let { user ->
        LaunchedEffect(user) {
            isLoading = true
            hasError = false

            val repoResult = viewModel.fetchUserRepo(user.repos_url)
            when (repoResult) {
                is ResourceState.Loading -> {
                    isLoading = true
                }
                is ResourceState.Success -> {
                    isLoading = false
                    repos = repoResult.data
                }

                is ResourceState.Error -> {
                    val error = repoResult.data
                    isLoading = false
                    hasError = true

                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }

            }

        }

        when {
            isLoading -> LoadingOverlay()
            hasError -> {}

            repos != null -> UserProfileAndRepoComponent(user, repos!!.toList()) { navController.popBackStack() } // Here, repos is of type Array<Repository>
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UserSearchScreenPreview() {
    UserSearchScreen(rememberNavController(), "festus")
}

