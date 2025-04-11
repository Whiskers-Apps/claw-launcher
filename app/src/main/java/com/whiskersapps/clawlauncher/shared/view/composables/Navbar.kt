package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@Composable
fun NavBar(
    navigateBack: () -> Unit,
    useCloseIcon: Boolean = false,
    endContent: @Composable RowScope.() -> Unit = {}
) {
    Column(
        Modifier.padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { navigateBack() },
                painter = if (useCloseIcon) painterResource(id = R.drawable.close) else painterResource(
                    id = R.drawable.back_arrow
                ),
                contentDescription = "back icon",
                tint = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                endContent()
            }
        }
    }
}