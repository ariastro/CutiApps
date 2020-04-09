package com.astronout.tmc.modules.admin.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAdminListResponseModel(
    @SerializedName("data")
    val getAdminListModel: List<GetAdminListModel>,
    @SerializedName("status")
    val status: Boolean
): Parcelable