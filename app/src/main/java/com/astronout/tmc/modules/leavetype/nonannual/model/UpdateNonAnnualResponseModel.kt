package com.astronout.tmc.modules.leavetype.nonannual.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateNonAnnualResponseModel(
    @SerializedName("data")
    val updateNonAnnualModel: List<UpdateNonAnnualModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)