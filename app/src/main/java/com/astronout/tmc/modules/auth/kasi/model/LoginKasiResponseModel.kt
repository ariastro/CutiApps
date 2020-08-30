package com.astronout.tmc.modules.auth.kasi.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginKasiResponseModel(
    @SerializedName("data")
    val loginKasiModel: LoginKasiModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)