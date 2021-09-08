package com.example.myapplication

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.ServiceInfo
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.accessibility.AccessibilityManager
import androidx.annotation.RequiresApi

fun Context.isAccessibilityServiceEnabled(
    service: Class<out AccessibilityService?>
): Boolean {
    val am = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    val enabledServices =
        am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
    for (enabledService in enabledServices) {
        val enabledServiceInfo: ServiceInfo = enabledService.resolveInfo.serviceInfo
        if (enabledServiceInfo.packageName.equals(packageName) && enabledServiceInfo.name.equals(
                service.name
            )
        ) return true
    }
    return false
}

@RequiresApi(Build.VERSION_CODES.P)
fun Context.loadGifFromAssets(assetFileName: String): Drawable {
    val source = ImageDecoder.createSource(assets, assetFileName)
    val drawable = ImageDecoder.decodeDrawable(source)
    if (drawable is AnimatedImageDrawable) {
        drawable.start()
    }
    return drawable
}