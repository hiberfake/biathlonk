package social.androiddev.hiberfake.biathlonk.settings.libraries

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.ui.ListItemDefaults
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.ArrowBack
import social.androiddev.hiberfake.biathlonk.core.ui.layout.plus
import social.androiddev.hiberfake.biathlonk.settings.R
import social.androiddev.hiberfake.biathlonk.core.ui.R as uiR

@Composable
internal fun LibrariesRoute(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
) {
    LibrariesScreen(
        onBackClick = onNavigateUp,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibrariesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val libraries by rememberLibraries()

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.libraries_title))
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
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { contentPadding ->
        LibrariesContainer(
            libraries = libraries,
            contentPadding = contentPadding.plus(bottom = 8.dp),
            padding = LibraryDefaults.libraryPadding(
                contentPadding = ListItemDefaults.ContainerPadding,
                licensePadding = LibraryDefaults.chipPadding(
//                    containerPadding = PaddingValues(top = 4.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                ),
                verticalPadding = 0.dp,
            ),
            textStyles = LibraryDefaults.libraryTextStyles(
                nameTextStyle = MaterialTheme.typography.titleMedium,
                licensesTextStyle = MaterialTheme.typography.bodySmall,
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun LibrariesScreenPreview() = BiathlonTheme {
    LibrariesScreen(onBackClick = {})
}
