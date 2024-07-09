package com.lighttigerxiv.clawlauncher.features.setup.permissions.ui

import android.os.Build

fun isAtLeastAndroid13(): Boolean{
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}