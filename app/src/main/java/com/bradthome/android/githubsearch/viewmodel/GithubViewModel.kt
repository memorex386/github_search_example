package com.bradthome.android.githubsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradthome.android.githubsearch.core.ResultState
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.repos.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GithubViewModel<T : ResultsItem>(
    val githubRepository: GithubRepository,
    val searchApis: SearchApis<T>,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow<ResultState<out Results<T>>>(ResultState.Empty())
    val state = _state.asStateFlow()

    val expanded = MutableStateFlow(searchApis.expandedUiItem)

    fun fetch(searchQuery: SearchQuery) {
        viewModelScope.launch(ioContext) {
            val result =
                githubRepository.fetch(searchOptions = SearchOptions(search = searchApis, searchQuery = searchQuery))
            _state.value = result

        }
    }

}
