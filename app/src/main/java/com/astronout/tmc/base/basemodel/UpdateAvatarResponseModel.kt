package com.astronout.tmc.base.basemodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateAvatarResponseModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)