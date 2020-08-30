package com.astronout.tmc.modules.leaves.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetLeaveByIdModel(
    @SerializedName("AnnualLeaveRights")
    val annualLeaveRights: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("EmpId")
    val empId: String,
    @SerializedName("empid")
    val empid: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("FromDate")
    val fromDate: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("kasi_acc")
    val kasiAcc: String,
    @SerializedName("kasi_remark")
    val kasiRemark: String?,
    @SerializedName("kasubag_acc")
    val kasubagAcc: String,
    @SerializedName("kasubag_remark")
    val kasubagRemark: String?,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("LeaveType")
    val leaveType: String,
    @SerializedName("manager_acc")
    val managerAcc: String,
    @SerializedName("manager_remark")
    val managerRemark: String?,
    @SerializedName("nomor_cuti")
    val nomorCuti: String?,
    @SerializedName("PostingDate")
    val postingDate: String,
    @SerializedName("RightsGranted")
    val rightsGranted: String,
    @SerializedName("ToDate")
    val toDate: String,
    @SerializedName("emp_department")
    val emp_department: String
): Parcelable