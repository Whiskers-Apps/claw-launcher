package com.whiskersapps.clawlauncher.settings.search_engines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.isUrl
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.RoundTextField

@Composable
fun AddSearchEngineDialog(
    onAction: (SearchEnginesScreenAction) -> Unit,
    name: String,
    query: String
) {

    Dialog(
        onDismissRequest = { onAction(SearchEnginesScreenAction.CloseAddEngineDialog) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 900.dp)
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
                        text = stringResource(R.string.SearchEnginesScreen_add_search_engine),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.Name),
                    color = MaterialTheme.colorScheme.onBackground
                )

                RoundTextField(
                    text = name,
                    placeholder = "DuckDuckGo",
                    onTextChange = { text ->
                        onAction(
                            SearchEnginesScreenAction.UpdateAddEngineDialogFields(
                                name = text,
                                query = query
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.SearchEnginesScreen_query),
                    color = MaterialTheme.colorScheme.onBackground
                )

                RoundTextField(
                    text = query,
                    placeholder = "https://duckduckgo.com/?q=%s",
                    onTextChange = { text ->
                        onAction(
                            SearchEnginesScreenAction.UpdateAddEngineDialogFields(
                                name = name,
                                query = text
                            )
                        )
                    })

                Spacer(modifier = Modifier.height(16.dp))
            }

            DialogFooter(
                onDismiss = { onAction(SearchEnginesScreenAction.CloseAddEngineDialog) },
                primaryButtonText = stringResource(R.string.Add),
                enabled = name.trim().isNotEmpty() && query.trim().isNotEmpty() && query.isUrl(),
                onPrimaryClick = { onAction(SearchEnginesScreenAction.AddEngine) }
            )
        }
    }
}