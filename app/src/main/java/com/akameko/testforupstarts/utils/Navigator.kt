package com.akameko.testforupstarts.utils

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.akameko.testforupstarts.R

/**
 * Screen navigation helper
 */
object Navigator {

    /**
     * Navigates to [DetailFragment] from fragment
     *
     * @param fragment to get havController from
     */
    fun navigateToDetailsFragment(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    /**
     * Navigates to [DetailFragment] from activity
     *
     * @param activity to get havController from
     */
    fun navigateToDetailsFragment(activity: Activity) {
        activity.findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_mainFragment_to_detailFragment)
    }

    /**
     * PopBackStack from view
     *
     * @param view to popBackStack from
     */
    fun backPressed(view: View) {
        view.findNavController().popBackStack()
    }
}