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

@Composable
fun AddBookmarkDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
) {
    Dialog(
        show = state.showAddBookmarkDialog,
        onDismiss = { onAction(BookmarksScreenAction.CloseAddBookmarkDialog) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            DialogHeader(
                icon = R.drawable.plus,
                title = stringResource(R.string.BookmarksScreen_add_bookmark)
            )

            Text(
                text = stringResource(R.string.Name),
                color = MaterialTheme.colorScheme.onBackground
            )

            RoundTextField(
                text = state.addBookmarkDialog.name,
                placeholder = "Notion",
                onTextChange = { text ->
                    onAction(
                        BookmarksScreenAction.UpdateAddBookmarkDialogFields(
                            name = text,
                            url = state.addBookmarkDialog.url
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Url", color = MaterialTheme.colorScheme.onBackground)

            RoundTextField(
                text = state.addBookmarkDialog.url,
                placeholder = "https://notion.so",
                onTextChange = { text ->
                    onAction(
                        BookmarksScreenAction.UpdateAddBookmarkDialogFields(
                            name = state.addBookmarkDialog.name,
                            url = text
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DialogFooter(
                onDismiss = { onAction(BookmarksScreenAction.CloseAddBookmarkDialog) },
                primaryButtonText = stringResource(R.string.Add),
                enabled = state.addBookmarkDialog.name.trim().isNotEmpty()
                        && state.addBookmarkDialog.url.isUrl(),
                onPrimaryClick = { onAction(BookmarksScreenAction.AddBookmark) }
            )
        }
    }
}