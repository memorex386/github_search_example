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

class GithubViewModel<T : ResultsItem>(
    val githubRepository: GithubRepository,
    val searchApis: SearchApis<T>,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow<ResultState<SearchResults<T>>>(ResultState.Empty())
    val state = _state.asStateFlow()

    fun fetch(searchQuery: SearchQuery) {
        _state.value = ResultState.Empty()
        viewModelScope.launch(ioContext) {
            _state.value = GitResult.Success(githubRepository.fetch(searchOptions = SearchOptions(search = searchApis,
                searchQuery = searchQuery)))
        }
    }

    fun updatePage(page: Int) {
        val value = state.value.successValue ?: return
        val query = value.searchOptions.searchQuery
        val results = value.state.successValue?.totalPages ?: return
        fetch(query.copy(page = 1.coerceAtLeast(min(results, page))))
    }

}
