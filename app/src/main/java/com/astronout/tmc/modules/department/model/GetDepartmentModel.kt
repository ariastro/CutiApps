package com.astronout.tmc.modules.department.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetDepartmentModel(
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
): Parcelable