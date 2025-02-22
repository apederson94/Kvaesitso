package de.mm20.launcher2.ui.settings.appearance

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.preferences.Settings.AppearanceSettings
import de.mm20.launcher2.preferences.Settings.AppearanceSettings.ColorScheme
import de.mm20.launcher2.preferences.Settings.AppearanceSettings.Theme
import de.mm20.launcher2.ui.R
import de.mm20.launcher2.ui.component.preferences.ListPreference
import de.mm20.launcher2.ui.component.preferences.Preference
import de.mm20.launcher2.ui.component.preferences.PreferenceCategory
import de.mm20.launcher2.ui.component.preferences.PreferenceScreen
import de.mm20.launcher2.ui.component.preferences.value
import de.mm20.launcher2.ui.locals.LocalNavController
import de.mm20.launcher2.ui.theme.getTypography

@Composable
fun AppearanceSettingsScreen() {
    val viewModel: AppearanceSettingsScreenVM = viewModel()
    val context = LocalContext.current
    val navController = LocalNavController.current
    val themeName by viewModel.colorSchemeName.collectAsStateWithLifecycle(null)
    PreferenceScreen(title = stringResource(id = R.string.preference_screen_appearance)) {
        item {
            PreferenceCategory {
                val theme by viewModel.theme.collectAsState()
                ListPreference(
                    title = stringResource(id = R.string.preference_theme),
                    items = listOf(
                        stringResource(id = R.string.preference_theme_system) to Theme.System,
                        stringResource(id = R.string.preference_theme_light) to Theme.Light,
                        stringResource(id = R.string.preference_theme_dark) to Theme.Dark,
                    ),
                    value = theme,
                    onValueChanged = { newValue ->
                        if (newValue == null) return@ListPreference
                        viewModel.setTheme(newValue)
                    }
                )
                Preference(
                    title = stringResource(id = R.string.preference_screen_colors),
                    summary = themeName,
                    onClick = {
                        navController?.navigate("settings/appearance/themes")
                    }
                )
                val font by viewModel.font.collectAsState()
                ListPreference(
                    title = stringResource(R.string.preference_font),
                    items = listOf(
                        "Outfit" to AppearanceSettings.Font.Outfit,
                        stringResource(R.string.preference_font_system) to AppearanceSettings.Font.SystemDefault,
                    ),
                    value = font,
                    onValueChanged = {
                        if (it != null) viewModel.setFont(it)
                    },
                    itemLabel = {
                        val typography = remember(it.value) {
                            getTypography(context, it.value)
                        }
                        Text(it.first, style = typography.titleMedium)
                    }
                )

                Preference(
                    title = stringResource(R.string.preference_cards),
                    summary = stringResource(R.string.preference_cards_summary),
                    onClick = {
                        navController?.navigate("settings/appearance/cards")
                    }
                )
            }
        }
    }
}