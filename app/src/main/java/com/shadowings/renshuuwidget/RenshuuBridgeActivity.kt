package com.shadowings.renshuuwidget

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity

class RenshuuBridgeActivity : ComponentActivity() {
    override fun onResume() {
        super.onResume()
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renshuu.org/me/"))
        startActivity(browserIntent)
    }
}