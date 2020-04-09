package com.astronout.tmc.modules.leavetype.annual.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAnnualResponseModel(
    @SerializedName("data")
    val getAnnualModel: List<GetAnnualModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable