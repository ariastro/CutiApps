package com.astronout.tmc.modules.department.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostNewDepartmentModel(
    @SerializedName("CreationDate")
    val creationDate: String,
    @SerializedName("DepartmentCode")
    val departmentCode: String,
    @SerializedName("DepartmentName")
    val departmentName: String,
    @SerializedName("DepartmentShortName")
    val departmentShortName: String,
    @SerializedName("id")
    val id: String
)