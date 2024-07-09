package com.whiskersapps.clawlauncher.shared.di

import android.app.Application
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(app: Application): SettingsRepository {
        return SettingsRepository(app)
    }

    @Singleton
    @Provides
    fun provideAppsRepository(
        app: Application
    ): AppsRepository {
        return AppsRepository(app)
    }
}