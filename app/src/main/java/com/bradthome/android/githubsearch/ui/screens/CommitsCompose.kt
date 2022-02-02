package com.bradthome.android.githubsearch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bradthome.android.githubsearch.ktx.DateUtil
import com.bradthome.android.githubsearch.models.Author
import com.bradthome.android.githubsearch.models.Commit
import com.bradthome.android.githubsearch.models.CommitItem
import com.bradthome.android.githubsearch.models.Repository
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import com.bradthome.android.githubsearch.ui.theme.MyColors


@Composable
fun CommitItemCompose(commitItem: CommitItem) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
            Column {
                Text(text = commitItem.repository?.fullName.orEmpty(),
                    fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)

                Text(text = commitItem.commit?.message.orEmpty(), maxLines = 2, color = MyColors.CoolBlue)

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = commitItem.commit?.author?.name.orEmpty(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp)
                    DateUtil.isoOffsetTime(commitItem.commit?.author?.date)?.also { date ->
                        Text(
                            text = "Committed on ${DateUtil.legibleString(date)}",
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                }
            }
        }
        Divider(color = Color.LightGray, thickness = 2.dp)
    }
}


@Preview(showBackground = true)
@Composable
fun CommitsPreview() {
    GithubSearchTheme {
        CommitItemCompose(CommitItem(commit =
        Commit(author = Author(date = "2021-06-06T23:37:31.000+10:00",
            name = "MeNow"), message = "sdfsd sdffds sdffsd "),
            repository = Repository(fullName = "sadfa/sdfsdf"))
        )
    }
}