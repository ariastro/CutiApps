package com.astronout.tmc.modules.employees.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class GetAllEmployeesModel(
    @SerializedName("Address")
    val address: String,
    @SerializedName("AnnualLeaveRights")
    val annualLeaveRights: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Department")
    val department: String,
    @SerializedName("Dob")
    val dob: String,
    @SerializedName("EmailId")
    val emailId: String,
    @SerializedName("EmpId")
    val empId: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("Gender")
    val gender: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Phonenumber")
    val phonenumber: String,
    @SerializedName("Position")
    val position: String,
    @SerializedName("RegDate")
    val regDate: String,
    @SerializedName("Status")
    val status: String
): Parcelable