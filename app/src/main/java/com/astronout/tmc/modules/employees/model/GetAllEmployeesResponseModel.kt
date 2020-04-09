package com.astronout.tmc.modules.employees.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAllEmployeesResponseModel(
    @SerializedName("data")
    val getAllEmployeesModel: List<GetAllEmployeesModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable