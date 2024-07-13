package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@Composable
fun NavBar(
    navigateBack: () -> Unit
){
    Box(Modifier.padding(16.dp)) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { navigateBack() },
            painter = painterResource(id = R.drawable.chevron_left),
            contentDescription = "back icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}