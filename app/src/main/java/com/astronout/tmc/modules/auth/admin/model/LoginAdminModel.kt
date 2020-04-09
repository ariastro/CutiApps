package com.astronout.tmc.modules.auth.admin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginAdminModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("updationDate")
    val updationDate: String,
    @SerializedName("admin_status")
    val adminStatus: String,
    @SerializedName("UserName")
    val userName: String
)