package com.bradthome.android.githubsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.bradthome.android.githubsearch.models.Query
import com.bradthome.android.githubsearch.models.SearchOptions
import com.bradthome.android.githubsearch.models.SearchRepositories
import com.bradthome.android.githubsearch.repos.GithubRepository
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var githubRepository: GithubRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
            lifecycleScope.launch {
                val result = githubRepository.fetch(SearchOptions(SearchRepositories, Query("As")))
                val temp = result
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GithubSearchTheme {
        Greeting("Android")
    }
}