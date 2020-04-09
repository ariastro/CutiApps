package com.astronout.tmc.modules.leavetype.nonannual.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetNonAnnualResponseModel(
    @SerializedName("data")
    val getNonAnnualModel: List<GetNonAnnualModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable