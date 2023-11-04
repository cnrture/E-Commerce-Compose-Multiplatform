package com.canerture.ecommercecm.ui.navigation

import androidx.compose.runtime.Composable
import com.canerture.ecommercecm.ui.detail.DetailRoute
import com.canerture.ecommercecm.ui.home.HomeRoute
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun NavGraph(navigator: Navigator = rememberNavigator()) {
    NavHost(
        navigator = navigator,
        initialRoute = "/home",
    ) {
        scene("/home") {
            HomeRoute { id ->
                navigator.navigate("/detail/$id")
            }
        }

        scene("/detail/{id}") {
            it.path<Int>("id")?.let { id ->
                DetailRoute(
                    id = id
                ) {
                    navigator.goBack()
                }
            }
        }
    }
}