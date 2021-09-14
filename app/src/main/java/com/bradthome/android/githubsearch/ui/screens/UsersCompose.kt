package com.bradthome.android.githubsearch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bradthome.android.githubsearch.models.UserItem
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun UsersCompose(data: List<UserItem>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = data) { userItem ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 20.dp)) {
                    GlideImage(imageModel = userItem.avatarUrl.orEmpty(),
                        requestOptions = RequestOptions().circleCrop(),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp))
                    Column(modifier = Modifier.padding(start = 6.dp)) {
                        Text(text = userItem.login.orEmpty(), color = Color(0XFF0969da))
                        Text(text = "Profile",
                            fontSize = 14.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                    }
                }
                Divider(color = Color.LightGray, thickness = 2.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersPreview() {
    GithubSearchTheme {
        UsersCompose(data = listOf(UserItem(login = "me now")))
    }
}