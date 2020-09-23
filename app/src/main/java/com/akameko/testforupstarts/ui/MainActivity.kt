package com.akameko.testforupstarts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.akameko.testforupstarts.R
import com.akameko.testforupstarts.utils.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            Navigator.navigateToMainFragment(this)
        }
    }
}