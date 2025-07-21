package com.example.versioncontrolappcomposable.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.utilities.CoreUtils
import com.example.utilities.ResourceState
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User
import com.example.versioncontrolappcomposable.ui.components.EditTextFieldWithScrollableListForRepos
import com.example.versioncontrolappcomposable.ui.components.LoadingOverlay
import com.example.versioncontrolappcomposable.ui.navigation.Routes
import com.example.versioncontrolappcomposable.ui.viewmodel.TransactionViewModel


@Composable
fun RepoSearchScreen(navController: NavController,
                     repo : String,
                     viewModel: TransactionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    if (!CoreUtils.isInternetAvailable(context)) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    LoadingOverlay("Searching repo: $repo")



    val repoSearchResponse by viewModel.repoSearch.collectAsState()
    LaunchedEffect(repo) {
        viewModel.searchGithubRepo(repo)
    }

    when (repoSearchResponse) {
        is ResourceState.Loading -> {
            println("Loading")
        }

        is ResourceState.Success -> {
            val response = (repoSearchResponse as ResourceState.Success).data
            println("repo search Success: $response")
            if (response.items.isEmpty()) {
                Routes.SEARCH_INFO = "We’ve searched the ends of the earth, repository not\nfound, please try again"
                navController.navigate(Routes.REPO_SCREEN)
            } else {
                EditTextFieldWithScrollableListForRepos(response.items) {navController.navigate("${Routes.SEARCH_REPO_SCREEN}/$it")}
            }
        }

        is ResourceState.Error -> {
            val error = (repoSearchResponse as ResourceState.Error).data
            println("repo search error: $error")
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }


    }
}

@Preview(showBackground = true)
@Composable
fun RepoSearchScreenPreview() {
    RepoSearchScreen(rememberNavController(), "festus")
}
