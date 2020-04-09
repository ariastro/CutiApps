package com.astronout.tmc.modules.leavetype.nonannual.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateNonAnnualModel(
    @SerializedName("AttachmentDoc")
    val attachmentDoc: String,
    @SerializedName("CreationDate")
    val creationDate: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("LeaveType")
    val leaveType: String,
    @SerializedName("No")
    val no: String,
    @SerializedName("RightsGranted")
    val rightsGranted: String
)