package com.sd.palatecraft.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

class RecipeUtils {

    companion object {

        // Function to open a URL in Chrome Custom Tabs
        fun openInChromeCustomTabs(context: Context, url: String) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

        // Function to share a recipe via a chooser dialog
        fun shareRecipe(context: Context, recipeUrl: String) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this recipe!")
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "I found this amazing recipe on PalateCraft: $recipeUrl"
            )
            val chooserIntent = Intent.createChooser(shareIntent, "Share Recipe")
            if (shareIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(chooserIntent)
            }
        }
    }
}
