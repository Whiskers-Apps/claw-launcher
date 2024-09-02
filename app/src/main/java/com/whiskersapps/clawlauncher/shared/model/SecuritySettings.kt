package com.whiskersapps.clawlauncher.shared.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class SecuritySettings: RealmObject{
    @PrimaryKey var _id: ObjectId = ObjectId()
    var hiddenApps: RealmList<String> = realmListOf()
    var secureApps: RealmList<String> = realmListOf()
}