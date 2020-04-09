package com.astronout.tmc.modules.auth.admin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginManagerModel(
    @SerializedName("manager_created")
    val managerCreated: String,
    @SerializedName("manager_id")
    val managerId: String,
    @SerializedName("manager_username")
    val managerUsername: String,
    @SerializedName("manager_status")
    val managerStatus: String,
    @SerializedName("token")
    val token: String
)