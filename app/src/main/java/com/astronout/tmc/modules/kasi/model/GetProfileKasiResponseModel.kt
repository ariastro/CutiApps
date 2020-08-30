package com.astronout.tmc.modules.kasi.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetProfileKasiResponseModel(
    @SerializedName("data")
    val getProfileKasiModel: GetProfileKasiModel,
    @SerializedName("status")
    val status: Boolean
): Parcelable