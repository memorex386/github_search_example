package com.bradthome.android.githubsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradthome.android.githubsearch.core.GitResult
import com.bradthome.android.githubsearch.core.ResultState
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.repos.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.min

typealias SearchResultsState<T> = ResultState<SearchResults<T>>

class GithubViewModel<T : ResultsItem>(
    val githubRepository: GithubRepository,
    val searchApis: SearchApis<T>,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow<SearchResultsState<T>>(ResultState.Empty())
    val state = _state.asStateFlow()

    private val _fetch = MutableStateFlow(false)
    val fetch = _fetch.asStateFlow()

    fun fetch(searchQuery: SearchQuery) {
        _fetch.value = true
        viewModelScope.launch(ioContext) {
            val searchOptions = SearchOptions(
                search = searchApis,
                searchQuery = searchQuery
            )
            val result = githubRepository.fetch(searchOptions)
            _state.value = GitResult.Success(
                SearchResults(
                    searchOptions = searchOptions,
                    state = result,
                    totalPages = result.successValue?.totalPages ?: _state.value.successValue?.totalPages
                )
            )
            _fetch.value = false
        }
    }

    fun updatePage(page: Int) {
        val value = state.value.successValue ?: return
        val query = value.searchOptions.searchQuery
        val results = value.totalPages ?: return
        fetch(query.copy(page = 1.coerceAtLeast(min(results, page))))
    }

}
