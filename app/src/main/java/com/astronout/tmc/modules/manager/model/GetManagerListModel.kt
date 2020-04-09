package com.astronout.tmc.modules.manager.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetManagerListModel(
    @SerializedName("manager_address")
    val managerAddress: String,
    @SerializedName("manager_avatar")
    val managerAvatar: String,
    @SerializedName("manager_birthday")
    val managerBirthday: String,
    @SerializedName("manager_city")
    val managerCity: String,
    @SerializedName("manager_country")
    val managerCountry: String,
    @SerializedName("manager_created")
    val managerCreated: String,
    @SerializedName("manager_gender")
    val managerGender: String,
    @SerializedName("manager_id")
    val managerId: String,
    @SerializedName("manager_name")
    val managerName: String,
    @SerializedName("manager_password")
    val managerPassword: String,
    @SerializedName("manager_phone")
    val managerPhone: String,
    @SerializedName("manager_status")
    val managerStatus: String,
    @SerializedName("manager_username")
    val managerUsername: String
): Parcelable