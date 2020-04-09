package com.astronout.tmc.modules.manager.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetManagerListResponseModel(
    @SerializedName("data")
    val getManagerListModel: List<GetManagerListModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable