package com.whiskersapps.clawlauncher.views.main.views.search.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@Composable
fun SearchBar(
    text: String,
    onChange: (text: String) -> Unit = {},
    onDone: () -> Unit = {},
    enabled: Boolean = true,
    placeholder: String = stringResource(id = R.string.Apps_search_apps)
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = { onChange(it) },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.search),
                contentDescription = "search icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        maxLines = 1,
        singleLine = true,
        enabled = enabled
    )
}