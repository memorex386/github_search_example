package com.bradthome.android.githubsearch.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.core.Constants
import com.bradthome.android.githubsearch.models.*

sealed class SearchScreen<R : ResultsItem>(
    @StringRes val titleRes: Int,
    val pathName: String,
    val navGraph: @Composable SearchScreen<R>.(NavController) -> Unit,
) {

    fun NavGraphBuilder.createNavGraph(navController: NavController) {
        composable(pathName) {
            navGraph(navController)
        }
    }

    companion object {
        val values by lazy {
            SearchScreen::class.sealedSubclasses.map { it.objectInstance as SearchScreen }
        }
    }

}

object Repositories :
    SearchScreen<GithubRepo>(titleRes = R.string.repositories, pathName = Constants.REPOSITORIES, navGraph = {
        RepositoriesCompose()
    })

object Users : SearchScreen<UserItem>(titleRes = R.string.users, pathName = Constants.USERS, navGraph = {
    RepositoriesCompose()
})

object Commits : SearchScreen<CommitItem>(titleRes = R.string.commits, pathName = Constants.COMMITS, navGraph = {
    RepositoriesCompose()
})

object Issues : SearchScreen<IssueItem>(titleRes = R.string.issues, pathName = Constants.ISSUES, navGraph = {
    RepositoriesCompose()
})