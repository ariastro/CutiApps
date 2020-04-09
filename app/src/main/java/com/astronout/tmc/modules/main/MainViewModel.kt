package com.astronout.tmc.modules.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.astronout.tmc.modules.auth.local.User

class MainViewModel(application: Application): AndroidViewModel(application) {

    fun clearData() {
        User.empId = ""
        User.firstName = ""
        User.lastName = ""
        User.email = ""
        User.gender = ""
        User.dob = ""
        User.position = ""
        User.department = ""
        User.address = ""
        User.city = ""
        User.country = ""
        User.phoneNumber = ""
        User.annualLeaveRights = ""
        User.token = ""
        User.idEmployee = ""

        User.adminId = ""
        User.managerId = ""
        User.userType = ""
    }

}