package com.astronout.tmc.modules.auth.admin.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginManagerResponseModel(
    @SerializedName("data")
    val loginManagerModel: LoginManagerModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)