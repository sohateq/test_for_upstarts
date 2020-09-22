package com.akameko.testforupstarts.utils

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akameko.testforupstarts.R


object Notificator {

    /**
     * Inflates [R.layout.notification_layout] to given [rootView] and hides after 3 seconds
     *
     * @param rootView ViewGroup to receive notification at the bottom
     * @param context Current context
     */
    fun showLikeNotification(rootView: View, context: Context?) {
        context ?: return

        val inflater = LayoutInflater.from(context)
        val cardViewNotification = inflater.inflate(R.layout.notification_layout, rootView as ViewGroup, false)
        rootView.addView(cardViewNotification)
        val h = Handler()
        h.postDelayed({ cardViewNotification.visibility = View.GONE }, 3000)
    }
}