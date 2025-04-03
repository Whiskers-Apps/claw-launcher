package com.whiskersapps.clawlauncher.launcher.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@Composable
fun SearchBar(
    text: String = "",
    onChange: (text: String) -> Unit = {},
    onDone: () -> Unit = {},
    enabled: Boolean = true,
    placeholder: String = stringResource(id = R.string.Search),
    borderRadius: Int = 100,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focus: Boolean = false,
    onFocused: () -> Unit = {},
) {

    val localDensity = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var fieldHeight by remember { mutableStateOf(0.dp) }

    LaunchedEffect(focus) {
        if (focus) {
            focusManager.clearFocus()
            focusRequester.requestFocus()
            onFocused()
        }
    }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(fieldHeight)
                .clip(RoundedCornerShape(borderRadius))
                .background(backgroundColor)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onGloballyPositioned {
                    fieldHeight = with(localDensity) { it.size.height.toDp() }
                },
            value = text,
            onValueChange = { onChange(it) },
            shape = RoundedCornerShape(borderRadius),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
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
}