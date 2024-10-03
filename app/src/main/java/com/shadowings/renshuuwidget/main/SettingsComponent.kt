package com.shadowings.renshuuwidget.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.shadowings.renshuuwidget.R
import com.shadowings.renshuuwidget.refresh.refreshWidget
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetThemeComponent
import com.shadowings.renshuuwidget.utils.logIfDebug
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import kotlinx.coroutines.launch

@PreviewLightDark
@Composable
fun SettingsComponentPreview() {
    RenshuuWidgetThemeComponent {
        Surface {
            SettingsComponent()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsComponent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize()
    ) {
        SettingsTitle()

        val prefs =
            context.getSharedPreferences(
                stringResource(R.string.prefs_key), Context.MODE_PRIVATE
            )

        val selectedThemeKey = stringResource(R.string.selected_theme_key)
        val itemPosition = remember {
            mutableIntStateOf(
                prefs.getInt(selectedThemeKey, 0)
            )
        }
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            themes.forEachIndexed { index, theme ->
                InputChip(
                    modifier = Modifier
                        .padding(2.dp),
                    selected = itemPosition.intValue == index,
                    onClick = {
                        logIfDebug("selected theme: $index")
                        itemPosition.intValue = index
                        prefs.edit().putInt(selectedThemeKey, index).apply()
                        scope.launch {
                            GlanceAppWidgetManager(context).getGlanceIds(
                                WidgetSmallRowComponent::class.java
                            )
                                .forEach { id ->
                                    refreshWidget(context, id)
                                }
                        }
                    },
                    label = { Text(theme.name) },
                    leadingIcon = {
                        if (itemPosition.intValue == index) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected"
                            )
                        }
                    },
                    colors = colorsFromTheme(theme)
                )
            }
        }

        ThemePreview(themes, itemPosition.intValue)
        Spacer(Modifier.size(24.dp))
    }
}

@Composable
private fun colorsFromTheme(theme: Theme): SelectableChipColors {
    return InputChipDefaults.inputChipColors().copy(
        containerColor = theme.backgroundColor,
        selectedContainerColor = theme.backgroundColor,
        labelColor = theme.textColor,
        selectedLabelColor = theme.textColor,
        leadingIconColor = Color.Transparent,
        selectedLeadingIconColor = theme.textColor
    )
}

@Composable
fun SettingsTitle() {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        text = "Choose the widget theme",
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ThemePreview(themes: Array<Theme>, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
    ) {
        Column(
            Modifier
                .background(themes[index].backgroundColor)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "The name of your schedule",
                textAlign = TextAlign.Center,
                color = themes[index].textColor,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Spacer(Modifier.size(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Learn: 25 - Review: 9999",
                textAlign = TextAlign.Center,
                color = themes[index].textColor,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp
                )
            )
        }
    }
}
