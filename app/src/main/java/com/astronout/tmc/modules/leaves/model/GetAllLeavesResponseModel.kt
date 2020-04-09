package com.astronout.tmc.modules.leaves.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAllLeavesResponseModel(
    @SerializedName("data")
    val getAllLeavesModel: List<GetAllLeavesModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable