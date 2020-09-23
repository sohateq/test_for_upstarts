package com.akameko.testforupstarts.utils

import android.app.Activity
import androidx.fragment.app.FragmentTransaction
import com.akameko.testforupstarts.R
import com.akameko.testforupstarts.ui.DetailFragment
import com.akameko.testforupstarts.ui.MainActivity
import com.akameko.testforupstarts.ui.MainFragment

/**
 * Screen navigation helper
 */
object Navigator {

    fun navigateToMainFragment(activity: Activity?) {
        if (activity !is MainActivity) return

        activity.supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment())
                .commitNow()
    }

    fun navigateToDetailsFragment(activity: Activity?) {
        if (activity !is MainActivity) return

        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment())
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }
}