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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.viewmodel.BookmarksScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.viewmodel.BookmarksScreenState

@Composable
fun AddBookmarkDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
) {

    Dialog(
        onDismissRequest = { onAction(BookmarksScreenAction.CloseAddBookmarkDialog) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Add Bookmark",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Name", color = MaterialTheme.colorScheme.onBackground)

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
            }

            DialogFooter(
                onDismiss = { onAction(BookmarksScreenAction.CloseAddBookmarkDialog) },
                primaryButtonText = "Add",
                enabled = true,
                onPrimaryClick = { onAction(BookmarksScreenAction.AddBookmark) }
            )
        }
    }
}