package com.astronout.tmc.modules.leaves.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAccLeavesResponseModel(
    @SerializedName("data")
    val getAccLeavesModel: List<GetAccLeavesModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable