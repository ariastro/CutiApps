package com.astronout.tmc.modules.admin.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetProfileAdminResponseModel(
    @SerializedName("data")
    val getProfileAdminModel: GetProfileAdminModel,
    @SerializedName("status")
    val status: Boolean
): Parcelable