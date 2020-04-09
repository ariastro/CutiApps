package com.astronout.tmc.modules.requestleave.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestLeaveResponseModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)