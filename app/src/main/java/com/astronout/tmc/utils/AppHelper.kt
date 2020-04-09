package com.astronout.tmc.utils

import com.astronout.tmc.modules.auth.local.User

object AppHelper {

    fun isLogedIn(): Boolean{
        val token = User.token
        return token.isNotEmpty()
    }

}