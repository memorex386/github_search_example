package com.bradthome.android.githubsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.core.GitResult
import com.bradthome.android.githubsearch.models.*
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import java.text.DecimalFormat

@Composable
fun <T : ResultsItem> PagerCompose(searchResults: SearchResults<T>, updatePage: (addPage: Int) -> Unit) {
    val currentPage = searchResults.searchOptions.searchQuery.page
    searchResults.totalPages?.also {
        val format = DecimalFormat("###,###,###,###")
        Column {
            Divider()
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                fun createModifer() = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                Icon(painterResource(id = R.drawable.ic_baseline_fast_rewind_24),
                    modifier = createModifer().clickable { updatePage(0) },
                    contentDescription = null)
                Icon(painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                    modifier = createModifer().clickable { updatePage(currentPage - 1) },

                    contentDescription = null)
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(format.format(currentPage))
                    }
                    append(" of ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(format.format(searchResults.totalPages))
                    }
                }, modifier = createModifer())
                Icon(painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                    modifier = createModifer().clickable { updatePage(currentPage + 1) },

                    contentDescription = null)
                Icon(painterResource(id = R.drawable.ic_baseline_fast_forward_24),
                    modifier = createModifer().clickable { updatePage(Int.MAX_VALUE) },

                    contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommitsPreview() {
    GithubSearchTheme {
        val result = GitResult.tryCatch {
            RepositoriesResponse(totalCount = 450045)
        }
        PagerCompose(
            SearchResults(SearchOptions(search = SearchRepositories,
                searchQuery = SearchQuery(Query("memor"), page = 1)),
                state = result,
                totalPages = result.successValue?.totalPages), {}
        )
    }
}