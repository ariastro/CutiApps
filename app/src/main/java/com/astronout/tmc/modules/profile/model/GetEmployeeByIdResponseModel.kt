package com.astronout.tmc.modules.profile.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetEmployeeByIdResponseModel(
    @SerializedName("data")
    val getEmployeeByIdModel: GetEmployeeByIdModel,
    @SerializedName("status")
    val status: Boolean
)