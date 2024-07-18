package com.whiskersapps.clawlauncher.shared.model

object Routes {
    object Setup {
        const val ROUTE = "setup"
        const val WELCOME = "welcome"
        const val LAYOUT = "minimal"
    }

    object Main {
        const val ROUTE = "main"
        const val HOME = "home"
        object Settings{
            const val ROUTE = "settings"
            const val MAIN = "settings-main"
            const val STYLE = "settings-style"
            const val HOME = "settings-home"
            const val APPS = "settings-apps"
            const val SEARCH = "settings-search"
            const val BOOKMARKS = "settings-bookmarks"
            const val SEARCH_ENGINES = "settings-search-engines"
            const val ABOUT = "settings-about"
        }
    }
}