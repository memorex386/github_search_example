package com.bradthome.android.githubsearch.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bradthome.android.githubsearch.models.ResultsItem


@Composable
fun <T : ResultsItem> SearchScreen<T>.RepositoriesCompose() {
    val title = stringResource(id = titleRes)
    LazyColumn {
        items(100) { message ->
            Text(text = title + message.toString())
        }
    }
}