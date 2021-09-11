package com.bradthome.android.githubsearch.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.core.Constants
import com.bradthome.android.githubsearch.core.ResultState
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.repos.GithubRepository
import com.bradthome.android.githubsearch.viewmodel.GithubViewModel

sealed class SearchScreen<R : ResultsItem>(
    val searchApis: SearchApis<R>,
    @StringRes val titleRes: Int,
    val pathName: String,
    val navGraph: @Composable SearchScreen<R>.(NavController, State<ResultState<out Results<R>>>) -> Unit,
) {

    fun NavGraphBuilder.createNavGraph(navController: NavController, githubRepository: GithubRepository) {

        composable(pathName) {
            val viewModel = GithubViewModel(githubRepository = githubRepository,
                scope = rememberCoroutineScope(),
                searchApis = searchApis)
            viewModel.fetch(SearchQuery(Query("a")))
            val state = viewModel.state.collectAsState()
            navGraph(navController, state)
        }
    }

    companion object {
        val values by lazy {
            SearchScreen::class.sealedSubclasses.map { it.objectInstance as SearchScreen }
        }
    }

}

object Repositories :
    SearchScreen<GithubRepo>(
        searchApis = SearchRepositories,
        titleRes = R.string.repositories,
        pathName = Constants.REPOSITORIES,
        navGraph = { navController, state ->
            RepositoriesCompose(state.value.successValue?.items.orEmpty())
        })

/**
object Users : SearchScreen<UserItem>(
searchApis = SearchUsers,
titleRes = R.string.users,
pathName = Constants.USERS,
navGraph = {
RepositoriesCompose()
})

object Commits : SearchScreen<CommitItem>(
searchApis = SearchCommits,
titleRes = R.string.commits,
pathName = Constants.COMMITS,
navGraph = {
RepositoriesCompose()
})

object Issues : SearchScreen<IssueItem>(
searchApis = SearchIssues,
titleRes = R.string.issues,
pathName = Constants.ISSUES,
navGraph = {
RepositoriesCompose()
})

 **/