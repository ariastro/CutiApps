package com.astronout.tmc.modules.kasi.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetKasiListResponseModel(
    @SerializedName("data")
    val getKasiListModel: List<GetKasiListModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable