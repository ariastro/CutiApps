package com.astronout.tmc.modules.leavetype.annual.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateAnnualTypeModel(
    @SerializedName("CreationDate")
    val creationDate: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("LeaveType")
    val leaveType: String,
    @SerializedName("No")
    val no: String
)