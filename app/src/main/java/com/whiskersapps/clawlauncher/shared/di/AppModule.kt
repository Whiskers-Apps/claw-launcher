package com.whiskersapps.clawlauncher.shared.di

import android.app.Application
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.BookmarksRepository
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.database.getRealm
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
        settingsRepository: SettingsRepository
    ): AppsRepository {
        return AppsRepository(app, settingsRepository)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(app: Application, realm: Realm): SettingsRepository {
        return SettingsRepository(app, realm)
    }

    @Singleton
    @Provides
    fun provideSearchEnginesRepository(
        realm: Realm,
        settingsRepository: SettingsRepository
    ): SearchEnginesRepository {
        return SearchEnginesRepository(realm, settingsRepository)
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return getRealm()
    }
}