package com.whiskersapps.clawlauncher.views.main.views.settings.views.about.model

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent.AboutScreenAction

class AboutScreenVM(
    private val app: Application
) : ViewModel() {

    fun onAction(action: AboutScreenAction){
        when(action){
            AboutScreenAction.NavigateBack -> {}
            AboutScreenAction.OpenRepoUrl -> openRepoUrl()
        }
    }

    private fun openRepoUrl() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Whiskers-Apps/claw-launcher")
            ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }

            app.startActivity(intent)
        } catch (e: Exception) {
            println("Error opening repo. $e")
        }
    }
}