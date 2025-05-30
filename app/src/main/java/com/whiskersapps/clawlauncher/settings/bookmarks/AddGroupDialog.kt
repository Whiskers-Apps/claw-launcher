package com.whiskersapps.clawlauncher.settings.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun AddGroupDialog(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit
) {
    Dialog(
        show = state.showAddGroupDialog,
        onDismiss = { onAction(BookmarksScreenAction.CloseAddGroupDialog) },
        scrollable = false
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                DialogHeader(
                    icon = R.drawable.plus,
                    title = stringResource(R.string.BookmarksScreen_add_group)
                )

                Text(
                    text = stringResource(R.string.Name),
                    color = MaterialTheme.colorScheme.onBackground
                )

                RoundTextField(
                    text = state.addGroupDialog.name,
                    placeholder = stringResource(R.string.BookmarksScreen_news),
                    onTextChange = { text ->
                        onAction(
                            BookmarksScreenAction.UpdateAddGroupDialogFields(name = text)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = state.addGroupDialog.bookmarks,
                key = { it.bookmark._id.toHexString() }
            ) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(
                                BookmarksScreenAction.ChangeAddGroupBookmarkSelection(
                                    item.bookmark._id,
                                    !item.selected
                                )
                            )
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(42.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        model = getCachedImageRequest(getFaviconUrl(item.bookmark.url)),
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
                                BookmarksScreenAction.ChangeAddGroupBookmarkSelection(
                                    item.bookmark._id,
                                    it
                                )
                            )
                        }
                    )
                }
            }

            item {
                DialogFooter(
                    onDismiss = { onAction(BookmarksScreenAction.CloseAddGroupDialog) },
                    primaryButtonText = stringResource(R.string.Add),
                    enabled = state.addGroupDialog.name.trim().isNotEmpty(),
                    onPrimaryClick = { onAction(BookmarksScreenAction.AddGroup) }
                )
            }
        }
    }
}