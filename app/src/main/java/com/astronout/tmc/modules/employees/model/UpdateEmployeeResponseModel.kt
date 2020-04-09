package com.astronout.tmc.modules.employees.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateEmployeeResponseModel(
    @SerializedName("data")
    val updateEmployeeModel: UpdateEmployeeModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)