package com.astronout.tmc.modules.department.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetDepartmentResponseModel(
    @SerializedName("data")
    val getDepartmentModel: List<GetDepartmentModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable