package com.whiskersapps.clawlauncher.shared.utils

val urlRegex = Regex("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)")

fun String.isUrl(): Boolean{
    return urlRegex.find(this) != null
}