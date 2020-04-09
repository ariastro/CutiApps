package com.astronout.tmc.modules.leaves.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetLeaveByIdModel(
    @SerializedName("AdminRemark")
    val adminRemark: String?,
    @SerializedName("AdminRemarkDate")
    val adminRemarkDate: String?,
    @SerializedName("Annual")
    val annual: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("empid")
    val empid: String,
    @SerializedName("FromDate")
    val fromDate: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("IsRead")
    val isRead: String,
    @SerializedName("LeaveType")
    val leaveType: String,
    @SerializedName("NonAnnual")
    val nonAnnual: String,
    @SerializedName("PostingDate")
    val postingDate: String,
    @SerializedName("RightsGranted")
    val rightsGranted: String,
    @SerializedName("Status")
    val status: String,
    @SerializedName("ToDate")
    val toDate: String,
    @SerializedName("EmpId")
    val EmpId: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("AnnualLeaveRights")
    val annualLeaveRights: String
): Parcelable