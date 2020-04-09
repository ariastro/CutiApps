package com.astronout.tmc.modules.auth.empolyees.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponseModel(
    @SerializedName("data")
    val loginModel: LoginModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)