package com.whiskersapps.clawlauncher.core.di

import com.whiskersapps.clawlauncher.core.db.getRealmDB
import com.whiskersapps.clawlauncher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.launcher.LauncherActivityVM
import com.whiskersapps.clawlauncher.launcher.LauncherScreenVM
import com.whiskersapps.clawlauncher.launcher.apps.di.AppsRepo
import com.whiskersapps.clawlauncher.launcher.bookmarks.BookmarksRepo
import com.whiskersapps.clawlauncher.search_engines.SearchEnginesRepo
import com.whiskersapps.clawlauncher.onboarding.OnBoardingActivityVM
import com.whiskersapps.clawlauncher.onboarding.select_engine_screen.SelectEngineScreenVM
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.settings.SettingsActivityVM
import com.whiskersapps.clawlauncher.launcher.apps.AppsScreenVM
import com.whiskersapps.clawlauncher.launcher.home.HomeScreenVM
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenVM
import com.whiskersapps.clawlauncher.settings.SettingsScreenVM
import com.whiskersapps.clawlauncher.settings.about.AboutScreenVM
import com.whiskersapps.clawlauncher.settings.apps.AppsSettingsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model.HomeSettingsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.model.SearchEnginesScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model.SecuritySettingsScreenVM
import com.whiskersapps.clawlauncher.settings.lock.LockScreenSettingsScreenVM
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        getRealmDB()
    }

    // ======================================================
    // Repositories
    // ======================================================

    single<SettingsRepo> {
        SettingsRepo(get(), get())
    }

    single<SearchEnginesRepo> {
        SearchEnginesRepo(get(), get())
    }

    single<BookmarksRepo> {
        BookmarksRepo(get())
    }

    single<AppsRepo> {
        AppsRepo(get(), get())
    }

    single<FoldableRepo> {
        FoldableRepo(get())
    }

    // ======================================================
    // ViewModels
    // ======================================================

    viewModel<OnBoardingActivityVM> {
        OnBoardingActivityVM(get(), get())
    }

    viewModel<SelectEngineScreenVM> {
        SelectEngineScreenVM(get(), get())
    }

    viewModel<LauncherActivityVM> {
        LauncherActivityVM(get())
    }

    viewModel<LauncherScreenVM> {
        LauncherScreenVM(get(), get())
    }

    viewModel<HomeScreenVM> {
        HomeScreenVM(get(), get())
    }

    viewModel<SearchScreenVM> {
        SearchScreenVM(get(), get(), get(), get(), get(), get())
    }

    viewModel<AppsScreenVM> {
        AppsScreenVM(get(), get(), get())
    }

    viewModel<SettingsActivityVM> {
        SettingsActivityVM(get())
    }

    viewModel<SettingsScreenVM> {
        SettingsScreenVM(get(), get())
    }

    viewModel<StyleSettingsScreenVM> {
        StyleSettingsScreenVM(get())
    }

    viewModel {
        HomeSettingsScreenVM(get())
    }

    viewModel {
        AppsSettingsScreenVM(get(), get())
    }

    viewModel {
        BookmarksScreenVM(get())
    }

    viewModel {
        SearchEnginesScreenVM(get())
    }

    viewModel {
        SecuritySettingsScreenVM(get(), get())
    }

    viewModel {
        LockScreenSettingsScreenVM(get())
    }

    viewModel {
        AboutScreenVM(get())
    }
}