package com.astronout.tmc.modules.manager.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetProfileManagerResponseModel(
    @SerializedName("data")
    val getProfileManagerModel: GetProfileManagerModel,
    @SerializedName("status")
    val status: Boolean
): Parcelable