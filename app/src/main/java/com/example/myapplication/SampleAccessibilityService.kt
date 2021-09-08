package com.example.myapplication

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.Build
import androidx.annotation.RequiresApi


class SampleAccessibilityService: AccessibilityService() {
    companion object {
        val TAG = SampleAccessibilityService::class.java.simpleName
    }
    
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent: $event")
        event.source?.apply {
            Log.d(TAG, "onAccessibilityEvent: parent: text: ${text}")
            for (i in 0 until childCount) {
                val view = getChild(i)
                Log.d(TAG, "onAccessibilityEvent: child: ${view.contentDescription}")
                if (view.contentDescription != null && view.contentDescription.contains("Record")) {
                    preformActionRecursively(view, AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
            recycle()
        }

    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
    }

    private fun preformActionRecursively(node: AccessibilityNodeInfo, action: Int){
        Log.d(TAG, "preformActionRecursively: node: $node")
        node.performAction(action)
        for(i in 0 until node.childCount) {
            preformActionRecursively(node.getChild(i), action)
        }
    }
}