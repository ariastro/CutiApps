package com.astronout.tmc.modules.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.astronout.tmc.R
import com.astronout.tmc.modules.main.AdminHomeActivity
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.main.MainActivity
import com.astronout.tmc.modules.main.ManagerHomeActivity
import com.astronout.tmc.utils.AppHelper
import com.astronout.tmc.utils.Constants.USER_EMPLOYEE
import com.astronout.tmc.utils.Constants.USER_MANAGER

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Int = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupDelayTime()

    }

    private fun setupDelayTime() {
        Handler().postDelayed({ nextActivity() }, SPLASH_TIME_OUT.toLong())
    }

    private fun nextActivity() {
        if (AppHelper.isLogedIn()) {
            startActivity(homeIntent(this))
            finish()
        } else {
            startActivity(loginIntent(this))
            finish()
        }
    }

    fun loginIntent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    fun homeIntent(context: Context): Intent {
        return when {
            User.userType == USER_EMPLOYEE -> Intent(context, MainActivity::class.java)
            User.userType == USER_MANAGER -> Intent(context, ManagerHomeActivity::class.java)
            else -> Intent(context, AdminHomeActivity::class.java)
        }
    }

}
