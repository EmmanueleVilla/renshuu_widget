package com.shadowings.renshuuwidget.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.shadowings.renshuuwidget.R
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetThemeComponent

@PreviewLightDark
@Composable
fun QAComponentPreview() {
    RenshuuWidgetThemeComponent {
        Surface {
            QAComponent()
        }
    }
}

@Composable
fun QAComponent() {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.blocked_tip)
        )
        Spacer(Modifier.size(48.dp))
        GithubButton()
    }
}