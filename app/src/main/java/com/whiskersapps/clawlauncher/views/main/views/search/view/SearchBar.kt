package com.whiskersapps.clawlauncher.views.main.views.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@Composable
fun SearchBar(
    text: String = "",
    onChange: (text: String) -> Unit = {},
    onDone: () -> Unit = {},
    enabled: Boolean = true,
    placeholder: String = stringResource(id = R.string.Apps_search_apps),
    borderRadius: Dp? = null,
    opacity: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focus: Boolean = false,
    onFocused: () -> Unit = {},
    showMenu: Boolean = false,
    onMenuClick: () -> Unit = {}
) {

    val localDensity = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val fieldShape =
        if (borderRadius == null) CircleShape else AbsoluteRoundedCornerShape(borderRadius)

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
                .clip(fieldShape)
                .alpha(opacity)
                .background(backgroundColor)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onGloballyPositioned { fieldHeight = with(localDensity){it.size.height.toDp()} },
            value = text,
            onValueChange = { onChange(it) },
            shape = fieldShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
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
            trailingIcon = {
                if (showMenu) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onMenuClick() },
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "menu icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
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