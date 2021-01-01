package org.dlo.myapplication.data

data class GithubUser(

    val total_count: Int,

    val incomplete_results: Boolean,

    val items: MutableList<User>
)