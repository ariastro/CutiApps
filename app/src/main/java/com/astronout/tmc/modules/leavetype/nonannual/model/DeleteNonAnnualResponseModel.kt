package com.astronout.tmc.modules.leavetype.nonannual.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteNonAnnualResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)