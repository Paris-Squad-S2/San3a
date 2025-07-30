package com.paris_2.san3a.presentation.util

import android.app.Activity
import java.lang.ref.WeakReference

interface ActivityProvider {
    fun getCurrentActivity(): Activity
    fun setCurrentActivity(activity: Activity)
}

class ActivityProviderImpl : ActivityProvider {
    private var currentActivity: WeakReference<Activity>? = null
    
    override fun setCurrentActivity(activity: Activity) {
        currentActivity = WeakReference(activity)
    }
    
    override fun getCurrentActivity(): Activity {
        return currentActivity?.get() ?: throw Exception("No activity available")
    }
}