package com.whiskersapps.clawlauncher.core.di

import android.app.Application
import com.whiskersapps.clawlauncher.core.db.getRealmDB
import com.whiskersapps.clawlauncher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.launcher.apps.AppsRepo
import com.whiskersapps.clawlauncher.launcher.bookmarks.BookmarksRepository
import com.whiskersapps.clawlauncher.launcher.search_engines.SearchEnginesRepo
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookmarksRepository(realm: Realm): BookmarksRepository {
        return BookmarksRepository(realm)
    }

    @Singleton
    @Provides
    fun provideAppsRepository(
        app: Application,
        settingsRepo: SettingsRepo
    ): AppsRepo {
        return AppsRepo(app, settingsRepo)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(app: Application, realm: Realm): SettingsRepo {
        return SettingsRepo(app, realm)
    }

    @Singleton
    @Provides
    fun provideSearchEnginesRepository(
        realm: Realm,
        settingsRepo: SettingsRepo
    ): SearchEnginesRepo {
        return SearchEnginesRepo(realm, settingsRepo)
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return getRealmDB()
    }

    @Singleton
    @Provides
    fun provideFoldableRepo(settingsRepo: SettingsRepo): FoldableRepo {
        return FoldableRepo(settingsRepo)
    }
}