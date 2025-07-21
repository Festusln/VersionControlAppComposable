package com.example.versioncontrolappcomposable.data.screenStateAndEvents

sealed class UserAndRepoSearchUiEvents {
    data class UsernameEntered(val user: String) : UserAndRepoSearchUiEvents()
    data class RepoNameEntered(val repoName: String) : UserAndRepoSearchUiEvents()
}
