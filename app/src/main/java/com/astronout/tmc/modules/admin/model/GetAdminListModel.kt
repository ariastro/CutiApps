package com.astronout.tmc.modules.admin.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAdminListModel(
    @SerializedName("admin_address")
    val adminAddress: String,
    @SerializedName("admin_avatar")
    val adminAvatar: String,
    @SerializedName("admin_birthday")
    val adminBirthday: String,
    @SerializedName("admin_city")
    val adminCity: String,
    @SerializedName("admin_country")
    val adminCountry: String,
    @SerializedName("admin_department")
    val adminDepartment: String,
    @SerializedName("admin_gender")
    val adminGender: String,
    @SerializedName("admin_name")
    val adminName: String,
    @SerializedName("admin_phone")
    val adminPhone: String,
    @SerializedName("admin_position")
    val adminPosition: String,
    @SerializedName("admin_status")
    val adminStatus: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("updationDate")
    val updationDate: String,
    @SerializedName("UserName")
    val userName: String
): Parcelable