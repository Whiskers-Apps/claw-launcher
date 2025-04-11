package com.whiskersapps.clawlauncher.bookmarks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class BookmarksShareActivity : ComponentActivity() {

    private var bookmarkName = ""
    private var bookmarkUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    bookmarkUrl = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
                    bookmarkName = intent.getStringExtra(Intent.EXTRA_SUBJECT) ?: ""
                }
            }
        }

        setContent {
            val vm = getViewModel<BookmarksShareActivityVM>()
            val settings = vm.settings.collectAsState().value

            if (settings != null) {
                ClawLauncherTheme(settings) {
                    BookmarksShareScreenRoot(initialName = bookmarkName, initialUrl = bookmarkUrl)
                }
            }
        }
    }
}