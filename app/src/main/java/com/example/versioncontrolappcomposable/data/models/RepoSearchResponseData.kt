package com.example.versioncontrolappcomposable.data.models

data class RepoSearchResponseData(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)

data class RepoOwner(
    val login: String,
    val id: Int,
    val node_id: String,
    val avatar_url: String,
    val url: String,
    val html_url: String
)

data class License(
    val key: String,
    val name: String,
    val spdx_id: String,
    val url: String?,
    val node_id: String
)


