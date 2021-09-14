package com.bradthome.android.githubsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.ktx.DateUtil
import com.bradthome.android.githubsearch.models.GithubRepo
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme


@Composable
fun RepositoriesCompose(data: List<GithubRepo>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = data) { repoItem ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
                    Image(painter = painterResource(id = R.drawable.ic_repo_icon),
                        contentDescription = "Repo Icon",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier.padding(top = 4.dp, end = 4.dp))
                    Column {
                        Text(text = repoItem.name.orEmpty(), color = Color(0XFF0969da))
                        Text(text = repoItem.description.orEmpty(), fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                            Image(painter = painterResource(id = R.drawable.ic_star_icon),
                                contentDescription = "Star Icon",
                                colorFilter = ColorFilter.tint(Color.Black),
                                modifier = Modifier.padding(end = 4.dp))
                            Text(text = repoItem.stargazersCount.toString(),
                                fontSize = 10.sp,
                                modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = repoItem.language.orEmpty(), fontSize = 10.sp,
                            )
                            Text(
                                text = DateUtil.isoDate(repoItem.updatedAt)?.let { date ->
                                    "Updated on ${DateUtil.legibleString(date)}"
                                }.orEmpty(),
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
                Divider(color = Color.LightGray, thickness = 2.dp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepoPreview() {
    GithubSearchTheme {
        RepositoriesCompose(data = listOf(
            GithubRepo(name = "Repo One",
                description = "One",
                language = "Kotlin",
                stargazersCount = 10,
                updatedAt = "2021-09-05T04:24:48Z"),
            GithubRepo(name = "Repo Two",
                description = "Two",
                language = "Ruby",
                stargazersCount = 20,
                updatedAt = "2021-09-05T04:24:48Z")))
    }
}