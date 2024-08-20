package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.intent.StyleSettingsScreenAction

@Composable
fun ThemeCard(
    name: String,
    backgroundColor: Color,
    secondaryBackgroundColor: Color,
    textColor: Color,
    accentColor: Color,
    onAccentColor: Color,
    onSetTheme: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(2.dp, color = secondaryBackgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onSetTheme() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .width(140.dp)
                .clip(CircleShape)
                .background(secondaryBackgroundColor)
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = textColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Search",
                color = textColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.width(140.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = onAccentColor
            ),
            onClick = { onSetTheme() }
        ) {
            Text(text = "Button", color = onAccentColor)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            color = textColor
        )
    }
}