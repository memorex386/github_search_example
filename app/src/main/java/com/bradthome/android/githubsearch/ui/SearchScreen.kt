package com.bradthome.android.githubsearch.ui

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
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
    @DrawableRes val iconRes: Int,
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
            val loading = viewModel.fetch.collectAsState()
            Column(modifier = Modifier.fillMaxSize()) {

                @Composable
                fun box(
                    create: @Composable BoxScope.() -> Unit,
                ) = Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    create()
                }

                @Composable
                fun progress() = box() {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                val state = viewModel.state.collectAsState()
                state.value.onResult(empty = {
                    progress()
                }, success = {
                    val searchResults = value
                    if (loading.value) {
                        progress()
                    } else
                        searchResults.state.onResult(success = {
                            LazyColumn(Modifier
                                .fillMaxWidth()
                                .weight(1f)) {
                                items(items = value.items.orEmpty()) {
                                    val context = LocalContext.current
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val browserIntent =
                                                Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl ?: return@clickable))
                                            startActivity(context, browserIntent, null)
                                        }) {
                                        navGraph(navController, it)
                                    }
                                }
                            }
                        }, error = {
                            box() {
                                Button(onClick = { viewModel.fetch(value.searchOptions.searchQuery) },
                                    modifier = Modifier.align(Alignment.Center)) {
                                    Text(text = "Retry")
                                }
                            }
                        })
                    PagerCompose(searchResults = searchResults, viewModel::updatePage)
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
        iconRes = R.drawable.ic_repo_icon,
        titleRes = R.string.repositories,
        pathName = Constants.REPOSITORIES,
        navGraph = { navController, state ->
            RepositoriesItemCompose(state)
        })

object Users : SearchScreen<UserItem>(
    searchApis = SearchUsers,
    iconRes = R.drawable.ic_baseline_person_24,
    titleRes = R.string.users,
    pathName = Constants.USERS,
    navGraph = { navController, state ->
        UsersItemCompose(state)
    })


object Commits : SearchScreen<CommitItem>(
    searchApis = SearchCommits,
    iconRes = R.drawable.github_commit,
    titleRes = R.string.commits,
    pathName = Constants.COMMITS,
    navGraph = { navController, state ->
        CommitItemCompose(state)
    })

object Issues : SearchScreen<IssueItem>(
    searchApis = SearchIssues,
    iconRes = R.drawable.ic_issues_icon,
    titleRes = R.string.issues,
    pathName = Constants.ISSUES,
    navGraph = { navController, state ->
        IssueItemCompose(state)
    })

