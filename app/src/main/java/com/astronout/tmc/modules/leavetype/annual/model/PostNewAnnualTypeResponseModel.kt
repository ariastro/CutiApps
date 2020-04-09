package com.astronout.tmc.modules.leavetype.annual.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostNewAnnualTypeResponseModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)