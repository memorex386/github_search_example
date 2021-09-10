package com.bradthome.android.githubsearch.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bradthome.android.githubsearch.models.ResultsItem


@Composable
fun <T : ResultsItem> SearchScreen<T>.RepositoriesCompose(data: List<ResultsItem>) {
    val title = stringResource(id = titleRes)
    Log.d("TEST", "${data}")
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = data) { item ->
            Text(text = item.htmlUrl ?: "sdfsf")
        }
    }
}