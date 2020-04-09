package com.astronout.tmc.modules.auth.local

import com.chibatching.kotpref.KotprefModel

object User : KotprefModel() {
    var empId by stringPref()
    var firstName by stringPref()
    var lastName by stringPref()
    var email by stringPref()
    var gender by stringPref()
    var dob by stringPref()
    var position by stringPref()
    var department by stringPref()
    var address by stringPref()
    var city by stringPref()
    var country by stringPref()
    var phoneNumber by stringPref()
    var annualLeaveRights by stringPref()
    var token by stringPref()
    var userAvatar by nullableStringPref()
    var idEmployee by stringPref()

    var adminId by stringPref()
    var adminStatus by stringPref()

    var managerId by stringPref()
    var managerStatus by stringPref()

    var userType by stringPref()
}