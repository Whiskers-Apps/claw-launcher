package com.whiskersapps.clawlauncher.settings.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.isUrl
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField
import com.whiskersapps.clawlauncher.settings.search_engines.SwipeToDelete

@Composable
fun EditBookmarkDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
) {

    Dialog(
        show = state.showEditBookmarkDialog,
        onDismiss = { onAction(BookmarksScreenAction.CloseEditBookmarkDialog) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            DialogHeader(
                icon = R.drawable.pencil,
                title = stringResource(R.string.BookmarksScreen_edit_bookmark)
            )

            Text(
                text = stringResource(R.string.Name),
                color = MaterialTheme.colorScheme.onBackground
            )

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

            DialogFooter(
                onDismiss = { onAction(BookmarksScreenAction.CloseEditBookmarkDialog) },
                primaryButtonText = stringResource(R.string.Save),
                enabled = state.editBookmarkDialog.name.trim().isNotEmpty()
                        && state.editBookmarkDialog.url.isUrl(),
                onPrimaryClick = { onAction(BookmarksScreenAction.EditBookmark) }
            )
        }
    }
}