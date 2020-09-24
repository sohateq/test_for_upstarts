package com.akameko.testforupstarts.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akameko.testforupstarts.R

/**
 * User notification helper
 */
object Notificator {

    /**
     * Inflates [R.layout.notification_layout] to given [rootView] and hides after 3 seconds
     *
     * @param rootView ViewGroup to receive notification at the bottom
     * @param context Current context
     */
    fun showNotification(rootView: View, message: String? = "Notification") {
        rootView.context ?: return

        val cardViewNotification = LayoutInflater.from(rootView.context).inflate(R.layout.notification_layout, rootView as ViewGroup, false)
        cardViewNotification.findViewById<TextView>(R.id.card_view_notification_text_view).text = message
        rootView.addView(cardViewNotification)
        Animator.fadeInAndOut(cardViewNotification)
    }
}