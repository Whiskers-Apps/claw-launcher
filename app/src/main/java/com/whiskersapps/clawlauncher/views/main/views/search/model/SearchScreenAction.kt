package com.whiskersapps.clawlauncher.views.main.views.search.model

import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.shared.model.AppShortcut.Shortcut
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup

/** Possible user actions in the search screen.*/
sealed class SearchScreenAction {
    data class OnSearchInput(val text: String) : SearchScreenAction()

    data class OnOpenApp(val packageName: String, val fragmentActivity: FragmentActivity) :
        SearchScreenAction()

    data class OnOpenShortcut(val appPackageName: String, val shortcut: Shortcut) :
        SearchScreenAction()

    data class OnOpenUrl(val url: String) : SearchScreenAction()

    data class OnRunAction(val fragmentActivity: FragmentActivity) : SearchScreenAction()

    data class OnSetFocusSearchBar(val focus: Boolean): SearchScreenAction()

    data class OnOpenAppInfo(val packageName: String): SearchScreenAction()

    data class OnRequestUninstall(val packageName: String): SearchScreenAction()

    data class OnOpenGroup(val group: BookmarkGroup): SearchScreenAction()

    data object OnClearSearch: SearchScreenAction()

    data object OnCloseSheet: SearchScreenAction()
}
