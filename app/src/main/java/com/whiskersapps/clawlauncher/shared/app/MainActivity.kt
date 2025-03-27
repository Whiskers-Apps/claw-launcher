package com.whiskersapps.clawlauncher.shared.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.launcher.LauncherActivity
import com.whiskersapps.clawlauncher.onboarding.OnBoardingActivity
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var settingsRepo: SettingsRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val context = this

        CoroutineScope(Dispatchers.Main).launch {
            val settings = settingsRepo.settingsFlow.first()
            val intent = Intent(
                context,
                if (settings.setupCompleted) LauncherActivity::class.java else OnBoardingActivity::class.java
            )

            context.startActivity(intent)
            context.finish()
        }
    }
}