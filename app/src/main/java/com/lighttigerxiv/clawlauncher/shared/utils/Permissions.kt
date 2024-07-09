package com.lighttigerxiv.clawlauncher.shared.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.R)
fun hasManageAllFilesPermission(): Boolean{
    return Environment.isExternalStorageManager()
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun hasReadMediaPermission(context: Context): Boolean{
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
}

