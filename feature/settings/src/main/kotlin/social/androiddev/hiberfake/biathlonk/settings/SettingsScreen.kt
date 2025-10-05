package social.androiddev.hiberfake.biathlonk.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.ArrowBack
import social.androiddev.hiberfake.biathlonk.settings.component.SettingsCategory
import social.androiddev.hiberfake.biathlonk.settings.component.SettingsItem
import social.androiddev.hiberfake.biathlonk.core.ui.R as uiR

@Composable
internal fun SettingsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToLibraries: () -> Unit,
    modifier: Modifier = Modifier,
) {
//    val context = LocalContext.current
//    val version = remember {
//        val packageInfo = context.packageInfo
//        "${packageInfo.versionName} (${PackageInfoCompat.getLongVersionCode(packageInfo)})"
//    }

    SettingsScreen(
//        version = version,
        onBackClick = onNavigateUp,
        onLibrariesClick = onNavigateToLibraries,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
//    version: String,
    onBackClick: () -> Unit,
    onLibrariesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(uiR.string.action_back),
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(contentPadding)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            SettingsCategory {
                SettingsItem(
                    label = stringResource(R.string.libraries_title),
                    onClick = onLibrariesClick,
                )
//                HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
//                SettingsItem(title = stringResource(R.string.settings_version)) {
//                    Text(text = version)
//                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenPreview() = BiathlonTheme {
    SettingsScreen(
//        version = "1.0 (1)",
        onBackClick = {},
        onLibrariesClick = {},
    )
}
