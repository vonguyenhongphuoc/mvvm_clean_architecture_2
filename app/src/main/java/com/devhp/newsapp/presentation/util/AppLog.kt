package com.devhp.newsapp.presentation.util

import android.util.Log

object AppLog {
    private const val APP_TAG = "AppTag"

    fun logD(content: String) {
        Log.d(APP_TAG, content)
    }
}