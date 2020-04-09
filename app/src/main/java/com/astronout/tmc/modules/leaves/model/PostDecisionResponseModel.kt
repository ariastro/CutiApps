package com.astronout.tmc.modules.leaves.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostDecisionResponseModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)