package com.astronout.tmc.modules.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private val _userProfile = MutableLiveData<GetEmployeeByIdModel>()
    val userProfile: LiveData<GetEmployeeByIdModel>
        get() = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getEmployeeById(User.idEmployee, {
            if (it.status) {
                saveToKotpref(it)
                setUserProfile(it.getEmployeeByIdModel)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setUserProfile(userProfile: GetEmployeeByIdModel) {
        _userProfile.value = userProfile
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun saveToKotpref(it: GetEmployeeByIdResponseModel){
        User.idEmployee = it.getEmployeeByIdModel.id
        User.firstName = it.getEmployeeByIdModel.firstName
        User.userAvatar = it.getEmployeeByIdModel.avatar
        User.lastName = it.getEmployeeByIdModel.lastName
        User.address = it.getEmployeeByIdModel.address
        User.annualLeaveRights = it.getEmployeeByIdModel.annualLeaveRights
        User.city = it.getEmployeeByIdModel.city
        User.country = it.getEmployeeByIdModel.country
        User.department = it.getEmployeeByIdModel.department
        User.empId = it.getEmployeeByIdModel.empId
        User.dob = it.getEmployeeByIdModel.dob
        User.email = it.getEmployeeByIdModel.emailId
        User.phoneNumber = it.getEmployeeByIdModel.phonenumber
        User.gender = it.getEmployeeByIdModel.gender
        User.position = it.getEmployeeByIdModel.position
    }

}