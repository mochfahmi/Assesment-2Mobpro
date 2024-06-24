package org.d3if3134.assesment2mobpro.navigation

import org.d3if3134.assesment2mobpro.ui.screen.KEY_ID_BAN
sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About:Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_BAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}