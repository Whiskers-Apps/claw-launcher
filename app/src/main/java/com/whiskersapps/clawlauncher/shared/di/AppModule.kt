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
    fun provideBookmarksRepository(): BookmarksRepository {
        return BookmarksRepository()
    }

    @Singleton
    @Provides
    fun provideAppsRepository(app: Application): AppsRepository {
        return AppsRepository(app)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(app: Application): SettingsRepository {
        return SettingsRepository(app)
    }

    @Singleton
    @Provides
    fun provideSearchEnginesRepository(realm: Realm, settingsRepository: SettingsRepository): SearchEnginesRepository {
        return SearchEnginesRepository(realm, settingsRepository)
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return getRealm()
    }
}