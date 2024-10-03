package com.shadowings.renshuuwidget.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shadowings.renshuuwidget.R

@Composable
fun GithubButtonComponent() {
    val context = LocalContext.current

    Button(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
            intent.data = android.net.Uri.parse("https://github.com/EmmanueleVilla/renshuu_widget")
            context.startActivity(intent)
        }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.git_contribute),
            textAlign = TextAlign.Center
        )
    }
}
