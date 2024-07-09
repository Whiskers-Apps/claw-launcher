package com.whiskersapps.clawlauncher.views.setup.permissions.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.views.setup.permissions.viewmodel.PermissionsScreenVM
import com.whiskersapps.clawlauncher.shared.view.theme.Typography


@Composable
fun PermissionsScreen(
    vm: PermissionsScreenVM = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLayout: () -> Unit,
    isAtLeastAndroid13: Boolean
) {

    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState().value

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { vm.checkPermissions() }
    )

    val readFilesPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            vm.checkPermissions()
        }
    )

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
                    text = stringResource(id = R.string.PermissionsScreen_permissions),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                if (isAtLeastAndroid13) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true)
                        ) {
                            Text(
                                text = stringResource(id = R.string.PermissionsScreen_read_all_media),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.PermissionsScreen_read_media_explain),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.labelMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = uiState.readMediaPermissions,
                            onCheckedChange = { storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                if (isAtLeastAndroid13) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                readFilesPermissionLauncher.launch(
                                    Intent(
                                        ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                        Uri.parse("package:" + context.packageName)
                                    )
                                )
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true)
                        ) {
                            Text(
                                text = stringResource(id = R.string.PermissionsScreen_manage_external_storage),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.PermissionsScreen_manage_external_storage_explain),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.labelMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = uiState.allFilesPermission,
                            onCheckedChange = {
                                readFilesPermissionLauncher.launch(
                                    Intent(
                                        ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                        Uri.parse("package:" + context.packageName)
                                    )
                                )
                            }
                        )
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

                Button(
                    onClick = { navigateToLayout() },
                    enabled = uiState.allFilesPermission && uiState.readMediaPermissions
                ) {
                    Text(
                        text = stringResource(id = R.string.SetupScreen_next),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}