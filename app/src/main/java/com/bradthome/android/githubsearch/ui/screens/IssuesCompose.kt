package com.bradthome.android.githubsearch.ui.screens

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
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
import com.bradthome.android.githubsearch.ui.theme.MyColors
import io.noties.markwon.Markwon


@Composable
fun IssueItemCompose(repoItem: IssueItem) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var canExpand by rememberSaveable { mutableStateOf<Boolean?>(null) }
    val angle: Float by animateFloatAsState(
        targetValue = if (!isExpanded) 0f else 180f,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_issues_icon),
                contentDescription = "Repo Icon",
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier.padding(top = 4.dp, end = 4.dp))
            Column {
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = repoItem.repositoryUrl.orEmpty()
                        .replace("${GithubApi.GITHUB_HOST}/repos/", ""),
                        fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                    Text(text = " #${repoItem.number}",
                        fontSize = 10.sp)
                }

                Text(text = repoItem.title.orEmpty(), color = MyColors.CoolBlue)

                repoItem.body?.takeIf { it.isNotBlank() }?.also {
                    Row {
                        val context = LocalContext.current
                        val markwon = Markwon.create(context);
                        val markdown = markwon.toMarkdown(repoItem.body.orEmpty());
                        val customLinkifyTextView = remember {
                            TextView(context)
                        }
                        AndroidView(modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(weight = 1f)
                            .animateContentSize(),
                            factory = { customLinkifyTextView }) { textView ->
                            textView.text = markdown
                            textView.maxLines = if (!isExpanded) 3 else Int.MAX_VALUE
                            textView.ellipsize = TextUtils.TruncateAt.END
                            if (canExpand == null) textView.post {
                                textView.layout?.apply {
                                    canExpand = (lineCount > 0 && getEllipsisCount(lineCount - 1) > 0)
                                }
                            }
                        }
                        Icon(Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { if (canExpand == true) isExpanded = !isExpanded }
                                .alpha(if (canExpand == true) 1f else 0f)
                                .padding(8.dp)
                                .rotate(angle))
                    }

                }

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)) {
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


@Preview(showBackground = true)
@Composable
fun IssuesPreview() {
    GithubSearchTheme {
        IssueItemCompose(IssueItem(
            title = "Load me now",
            body = "Body to show",
            repositoryUrl = "https://api.github.com/repos/michaelday008/AnyRPGCore",
        ))
    }
}