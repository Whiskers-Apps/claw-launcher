package com.whiskersapps.clawlauncher.views.main.views.settings.views.about.model

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent.AboutScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutScreenVM @Inject constructor(
    private val app: Application
) : ViewModel() {

    fun onAction(action: AboutScreenAction){
        when(action){
            AboutScreenAction.NavigateBack -> {}
            AboutScreenAction.OpenRepoUrl -> openRepoUrl()
            AboutScreenAction.OpenDonateLink -> openDonateLink()
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

    private fun openDonateLink() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://buymeacoffee.com/lighttigerxiv")
            ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }

            app.startActivity(intent)
        } catch (e: Exception) {
            println("Error opening donate link. $e")
        }
    }
}