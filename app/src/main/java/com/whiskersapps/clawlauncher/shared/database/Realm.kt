package com.whiskersapps.clawlauncher.shared.database

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.model.SecuritySettings
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun getRealm(): Realm {
    val config = RealmConfiguration.Builder(
        schema = setOf(
            SearchEngine::class,
            Bookmark::class,
            BookmarkGroup::class,
            SecuritySettings::class
        )
    ).schemaVersion(1)
        .compactOnLaunch()
        .build()

    return Realm.open(config)
}