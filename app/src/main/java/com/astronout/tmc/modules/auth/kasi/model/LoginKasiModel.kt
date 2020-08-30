package com.astronout.tmc.modules.auth.kasi.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginKasiModel(
    @SerializedName("kasi_created")
    val kasiCreated: String,
    @SerializedName("kasi_id")
    val kasiId: String,
    @SerializedName("kasi_jenis")
    val kasiJenis: String,
    @SerializedName("kasi_status")
    val kasiStatus: String,
    @SerializedName("kasi_username")
    val kasiUsername: String,
    @SerializedName("kasi_jabatan")
    val kasiJabatan: String,
    @SerializedName("token")
    val token: String
)