package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.isUrl
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.intent.BookmarksScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenState
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SwipeToDelete

@Composable
fun EditBookmarkDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
) {

    Dialog(
        show = state.showEditBookmarkDialog,
        onDismiss = { onAction(BookmarksScreenAction.CloseEditBookmarkDialog) }
    ) {
        DialogHeader(icon = R.drawable.pencil, title = "Edit Bookmark")

        Text(text = "Name", color = MaterialTheme.colorScheme.onBackground)

        RoundTextField(
            text = state.editBookmarkDialog.name,
            placeholder = "Notion",
            onTextChange = { text ->
                onAction(
                    BookmarksScreenAction.UpdateEditBookmarkDialogFields(
                        state.editBookmarkDialog.copy(name = text)
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Url", color = MaterialTheme.colorScheme.onBackground)

        RoundTextField(
            text = state.editBookmarkDialog.url,
            placeholder = "https://notion.so",
            onTextChange = { text ->
                onAction(
                    BookmarksScreenAction.UpdateEditBookmarkDialogFields(
                        state.editBookmarkDialog.copy(url = text)
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SwipeToDelete { onAction(BookmarksScreenAction.DeleteBookmark) }

        Spacer(modifier = Modifier.height(16.dp))

        DialogFooter(
            onDismiss = { onAction(BookmarksScreenAction.CloseEditBookmarkDialog) },
            primaryButtonText = "Save",
            enabled = state.editBookmarkDialog.name.trim().isNotEmpty()
                    && state.editBookmarkDialog.url.isUrl(),
            onPrimaryClick = { onAction(BookmarksScreenAction.EditBookmark) }
        )
    }
}