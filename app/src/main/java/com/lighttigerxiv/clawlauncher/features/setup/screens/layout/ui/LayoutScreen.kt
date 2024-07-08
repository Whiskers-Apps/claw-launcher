package com.lighttigerxiv.clawlauncher.features.setup.screens.layout.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lighttigerxiv.clawlauncher.R
import com.lighttigerxiv.clawlauncher.features.setup.screens.layout.viewmodel.LayoutScreenVM

@Composable
fun LayoutScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    vm: LayoutScreenVM = hiltViewModel()
) {

    val uiState = vm.uiState.collectAsState().value

    uiState?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = stringResource(id = R.string.LayoutScreen_style),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { vm.updateLayout("minimal") }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            painter = painterResource(id = R.drawable.scenery),
                            contentDescription = "setting wallpaper",
                            contentScale = ContentScale.Crop,
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "14:20",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = "Sunday - 14/04/2001",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(selected = uiState.layout == "minimal", onClick = { })
                        Text(text = stringResource(id = R.string.LayoutScreen_minimal))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { vm.updateLayout("bubbly") }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            painter = painterResource(id = R.drawable.scenery),
                            contentDescription = "setting wallpaper",
                            contentScale = ContentScale.Crop,
                        )

                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .alpha(0.8f)
                                .blur(20.dp)
                                .background(Color.White)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "14:20",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                text = "Sunday - 14/04/2001",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(selected = uiState.layout == "bubbly", onClick = { })
                        Text(text = stringResource(id = R.string.LayoutScreen_bubbly))
                    }
                }


            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = { navigateBack() }) {
                    Text(
                        text = stringResource(id = R.string.SetupScreen_previous),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { vm.finishSetup { navigateToHome() } }) {
                    Text(
                        text = stringResource(id = R.string.SetupScreen_finish),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}