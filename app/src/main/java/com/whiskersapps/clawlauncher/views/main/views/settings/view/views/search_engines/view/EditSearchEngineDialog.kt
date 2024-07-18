package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.search_engines.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.search_engines.intent.SearchEnginesScreenAction

@Composable
fun EditSearchEngineDialog(
    onAction: (SearchEnginesScreenAction) -> Unit,
    name: String,
    query: String,
    default: Boolean
) {

    Dialog(
        onDismissRequest = { onAction(SearchEnginesScreenAction.CloseEditEngineDialog) },
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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Edit Search Engine",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(32.dp))
            }


            Text(text = "Name", color = MaterialTheme.colorScheme.onBackground)

            RoundTextField(
                text = name,
                placeholder = "DuckDuckGo",
                onTextChange = { text ->
                    onAction(
                        SearchEnginesScreenAction.UpdateEditEngineDialogFields(
                            name = text,
                            query = query,
                            default = default
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Query", color = MaterialTheme.colorScheme.onBackground)

            RoundTextField(
                text = query,
                placeholder = "https://duckduckgo.com/?q=%s",
                onTextChange = { text ->
                    onAction(
                        SearchEnginesScreenAction.UpdateEditEngineDialogFields(
                            name = name,
                            query = text,
                            default = default
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SwitchSetting(
                title = "Default",
                description = "Make it the default search engine",
                value = default,
                onValueChange = { selected ->
                    onAction(
                        SearchEnginesScreenAction.UpdateEditEngineDialogFields(
                            name = name,
                            query = query,
                            default = selected
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SwipeToDelete { onAction(SearchEnginesScreenAction.DeleteEngine) }

            Spacer(modifier = Modifier.height(16.dp))

            DialogFooter(
                onDismiss = { onAction(SearchEnginesScreenAction.CloseEditEngineDialog) },
                primaryButtonText = "Save",
                enabled = name.trim().isNotEmpty() && query.trim().isNotEmpty(),
                onPrimaryClick = { onAction(SearchEnginesScreenAction.SaveEditEngine) }
            )
        }
    }
}