package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.intent.BookmarksScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenState

@Composable
fun EditGroupDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
){
    Dialog(
        onDismissRequest = { onAction(BookmarksScreenAction.CloseEditGroupDialog) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

            Column {

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
                        text = "Edit Group",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Name", color = MaterialTheme.colorScheme.onBackground)

                RoundTextField(
                    text = state.editGroupDialog.name,
                    placeholder = "Notion",
                    onTextChange = { text ->
                        onAction(
                            BookmarksScreenAction.UpdateEditGroupDialogFields(name = text)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(
                        items = state.editGroupDialog.bookmarks,
                        key = { it.bookmark._id.toHexString() }
                    ) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(42.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                model = getFaviconUrl(item.bookmark.url),
                                contentDescription = "${item.bookmark.name} icon"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f, fill = true)
                            ) {
                                Text(
                                    text = item.bookmark.name,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Text(
                                    text = item.bookmark.url,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = Typography.labelSmall
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Switch(
                                checked = item.selected,
                                onCheckedChange = {
                                    onAction(
                                        BookmarksScreenAction.ChangeEditGroupBookmarkSelection(
                                            item.bookmark._id,
                                            it
                                        )
                                    )
                                })
                        }
                    }
                }
            }

            DialogFooter(
                onDismiss = { onAction(BookmarksScreenAction.CloseEditGroupDialog) },
                primaryButtonText = "Save",
                enabled = state.editGroupDialog.name.trim().isNotEmpty(),
                onPrimaryClick = { onAction(BookmarksScreenAction.SaveGroupEdit) }
            )
        }
    }
}