package com.bradthome.android.githubsearch.viewmodel

import com.bradthome.android.githubsearch.core.ResultState
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.repos.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GithubViewModel<T : ResultsItem>(
    val githubRepository: GithubRepository,
    val searchApis: SearchApis<T>,
    private val scope: CoroutineScope,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) {
    private val _state = MutableStateFlow<ResultState<out Results<T>>>(ResultState.Empty())
    val state = _state.asStateFlow()

    fun fetch(searchQuery: SearchQuery) {
        scope.launch(ioContext) {
            val result =
                githubRepository.fetch(searchOptions = SearchOptions(search = searchApis, searchQuery = searchQuery))
            _state.value = result

        }
    }

}