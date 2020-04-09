package com.astronout.tmc.modules.leaves.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetLeaveByIdResponseModel(
    @SerializedName("data")
    val getLeaveByIdModel: List<GetLeaveByIdModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable