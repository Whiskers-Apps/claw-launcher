package com.whiskersapps.clawlauncher.core

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whiskersapps.clawlauncher.launcher.LauncherActivity
import com.whiskersapps.clawlauncher.onboarding.OnBoardingActivity
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsRepo = get<SettingsRepo>()
        val activity = this

        CoroutineScope(Dispatchers.Main).launch {
            val settings = settingsRepo.settingsFlow.first()
            val intent = Intent(
                activity,
                if (settings.setupCompleted) LauncherActivity::class.java else OnBoardingActivity::class.java
            )

            activity.startActivity(intent)
            activity.finish()
        }
    }
}