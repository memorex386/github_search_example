package com.bradthome.android.githubsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bradthome.android.githubsearch.repos.GithubRepository
import com.bradthome.android.githubsearch.ui.SearchScreen
import com.bradthome.android.githubsearch.ui.theme.GithubSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var githubRepository: GithubRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(githubRepository = githubRepository)
        }
    }
}

@Composable
fun MainScreen(githubRepository: GithubRepository) {
    GithubSearchTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    SearchScreen.values.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(painterResource(id = screen.iconRes),
                                    modifier = Modifier.size(20.dp, 20.dp),
                                    contentDescription = null)
                            },
                            label = { Text(stringResource(id = screen.titleRes)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.pathName } == true,
                            onClick = {
                                navController.navigate(screen.pathName) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = SearchScreen.values[0].pathName) {
                SearchScreen.values.forEach {
                    it.apply {
                        createNavGraph(navController = navController,
                            githubRepository = githubRepository)
                    }
                }
            }
        }
    }

}
