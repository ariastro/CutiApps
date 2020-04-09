package com.astronout.tmc.modules.department.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostNewDepartmentResponseModel(
    @SerializedName("data")
    val postNewDepartmentModel: List<PostNewDepartmentModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)