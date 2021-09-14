package com.bradthome.android.githubsearch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bradthome.android.githubsearch.models.UserItem
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import com.bradthome.android.githubsearch.ui.theme.MyColors
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun UsersItemCompose(userItem: UserItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
            GlideImage(imageModel = userItem.avatarUrl.orEmpty(),
                requestOptions = RequestOptions().circleCrop(),
                modifier = Modifier.size(width = 24.dp, height = 24.dp))
            Column(modifier = Modifier.padding(start = 6.dp)) {
                Text(text = userItem.login.orEmpty(), color = MyColors.CoolBlue)

            }
        }
        Divider(color = Color.LightGray, thickness = 2.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun UsersPreview() {
    GithubSearchTheme {
        UsersItemCompose(UserItem(login = "me now"))
    }
}