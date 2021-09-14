package com.bradthome.android.githubsearch.ui.screens

import android.widget.TextView
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bradthome.android.githubsearch.R
import com.bradthome.android.githubsearch.ktx.DateUtil
import com.bradthome.android.githubsearch.models.IssueItem
import com.bradthome.android.githubsearch.network.GithubApi
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import io.noties.markwon.Markwon


@Composable
fun IssuesCompose(data: List<IssueItem>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = data) { repoItem ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
                    Image(painter = painterResource(id = R.drawable.ic_issues_icon),
                        contentDescription = "Repo Icon",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier.padding(top = 4.dp, end = 4.dp))
                    Column {
                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            Text(text = repoItem.repositoryUrl.orEmpty().replace("${GithubApi.GITHUB_HOST}/repos", ""),
                                fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                            Text(text = " #${repoItem.number}",
                                fontSize = 10.sp)
                        }

                        Text(text = repoItem.title.orEmpty(), color = Color(0XFF0969da))

                        val context = LocalContext.current
                        // obtain an instance of Markwon
                        val markwon = Markwon.create(context);

// parse markdown and create styled text
                        val markdown = markwon.toMarkdown(repoItem.body.orEmpty());
                        val customLinkifyTextView = remember {
                            TextView(context)
                        }
                        AndroidView(modifier = Modifier.padding(), factory = { customLinkifyTextView }) { textView ->
                            textView.text = markdown
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                            Image(painter = painterResource(id = R.drawable.ic_star_icon),
                                contentDescription = "Star Icon",
                                colorFilter = ColorFilter.tint(Color.Black),
                                modifier = Modifier.padding(end = 4.dp))
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
fun IssuesPreview() {
    GithubSearchTheme {
        IssuesCompose(data = listOf(IssueItem(
            title = "Load me now",
            body = "Body to show",
            repositoryUrl = "https://api.github.com/repos/michaelday008/AnyRPGCore",
        )))
    }
}