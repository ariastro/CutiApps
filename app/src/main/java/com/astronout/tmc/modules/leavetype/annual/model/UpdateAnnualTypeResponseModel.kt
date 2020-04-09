package com.astronout.tmc.modules.leavetype.annual.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateAnnualTypeResponseModel(
    @SerializedName("data")
    val updateAnnualTypeModel: List<UpdateAnnualTypeModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)