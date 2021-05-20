package org.eveh.plantdetector

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

class ApplicationBase: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)
    }
}