package com.example.recipeapp.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent


fun Context.openCustomTab(url: String){
    val intent = CustomTabsIntent.Builder()
        .setInitialActivityHeightPx(400)
        .setCloseButtonPosition(CustomTabsIntent.CLOSE_BUTTON_POSITION_END)
        .setToolbarCornerRadiusDp(10)
        .build()

    intent.launchUrl(this, Uri.parse(url))

}