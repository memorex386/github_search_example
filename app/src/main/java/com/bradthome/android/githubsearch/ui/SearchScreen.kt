package com.bradthome.android.githubsearch.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.core.Constants
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.repos.GithubRepository
import com.bradthome.android.githubsearch.ui.screens.CommitItemCompose
import com.bradthome.android.githubsearch.ui.screens.IssueItemCompose
import com.bradthome.android.githubsearch.ui.screens.RepositoriesItemCompose
import com.bradthome.android.githubsearch.ui.screens.UsersItemCompose
import com.bradthome.android.githubsearch.viewmodel.GithubViewModel

sealed class SearchScreen<R : ResultsItem>(
    val searchApis: SearchApis<R>,
    @StringRes val titleRes: Int,
    val pathName: String,
    val navGraph: @Composable SearchScreen<R>.(NavController, R) -> Unit,
) {

    fun NavGraphBuilder.createNavGraph(
        navController: NavController,
        githubRepository: GithubRepository,
    ) {
        composable(pathName) {
            val viewModel: GithubViewModel<R> = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return GithubViewModel(githubRepository = githubRepository,
                        searchApis = searchApis).also {
                        it.fetch(SearchQuery(Query("a")))
                    } as T
                }
            }, key = "${searchApis.title}NavStateViewModel")
            Box(modifier = Modifier.fillMaxSize()) {
                val state = viewModel.state.collectAsState()
                state.value.onResult(empty = {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }, error = {
                    Text(text = exception.message ?: "Broken Call", modifier = Modifier.align(Alignment.Center))
                }, success = {
                    val searchResults = value
                    searchResults.state.onResult(success = {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(Modifier
                                .fillMaxWidth()
                                .weight(1f)) {
                                items(items = value.items.orEmpty()) {
                                    navGraph(navController, it)
                                }
                            }
                            PagerCompose(searchResults = searchResults, viewModel::updatePage)
                        }
                    }, error = {
                        Button(onClick = { viewModel.fetch(value.searchOptions.searchQuery) }) {
                            Text(text = "Retry")
                        }
                    })

                })
            }
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
            RepositoriesItemCompose(state)
        })

object Users : SearchScreen<UserItem>(
    searchApis = SearchUsers,
    titleRes = R.string.users,
    pathName = Constants.USERS,
    navGraph = { navController, state ->
        UsersItemCompose(state)
    })


object Commits : SearchScreen<CommitItem>(
    searchApis = SearchCommits,
    titleRes = R.string.commits,
    pathName = Constants.COMMITS,
    navGraph = { navController, state ->
        CommitItemCompose(state)
    })

object Issues : SearchScreen<IssueItem>(
    searchApis = SearchIssues,
    titleRes = R.string.issues,
    pathName = Constants.ISSUES,
    navGraph = { navController, state ->
        IssueItemCompose(state)
    })

