package com.astronout.tmc.modules.kasi.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetKasiListModel(
    @SerializedName("kasi_address")
    val kasiAddress: String,
    @SerializedName("kasi_avatar")
    val kasiAvatar: String,
    @SerializedName("kasi_birthday")
    val kasiBirthday: String,
    @SerializedName("kasi_city")
    val kasiCity: String,
    @SerializedName("kasi_country")
    val kasiCountry: String,
    @SerializedName("kasi_created")
    val kasiCreated: String,
    @SerializedName("kasi_gender")
    val kasiGender: String,
    @SerializedName("kasi_id")
    val kasiId: String,
    @SerializedName("kasi_jabatan")
    val kasiJabatan: String,
    @SerializedName("kasi_jenis")
    val kasiJenis: String,
    @SerializedName("kasi_name")
    val kasiName: String,
    @SerializedName("kasi_password")
    val kasiPassword: String,
    @SerializedName("kasi_phone")
    val kasiPhone: String,
    @SerializedName("kasi_status")
    val kasiStatus: String,
    @SerializedName("kasi_username")
    val kasiUsername: String
): Parcelable