package com.astronout.tmc.modules.auth.empolyees.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.empolyees.model.LoginResponseModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.USER_EMPLOYEE
import com.astronout.tmc.utils.logDebug

class LoginViewModel(application: Application): AndroidViewModel(application) {

    val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val _isLogedIn = MutableLiveData<Boolean>()
    val isLogedIn: LiveData<Boolean>
        get() = _isLogedIn

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLogin(isLogedIn: Boolean) {
        _isLogedIn.value = isLogedIn
    }

    fun loginEmployees() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.postLogin(userName.value!!, password.value!!, {
            if (it.status) {
                setLogin(true)
                saveToKotpref(it)
            } else {
                setLogin(false)
            }
        }, {
            logDebug("ErrorLoginEmployees: $it")
        }, {
            setLoading(false)
        })
    }

    fun saveToKotpref(it: LoginResponseModel){
        User.idEmployee = it.loginModel.id
        User.token = it.loginModel.token
        User.firstName = it.loginModel.firstName
        User.lastName = it.loginModel.lastName
        User.country = it.loginModel.country
        User.address = it.loginModel.address
        User.annualLeaveRights = it.loginModel.annualLeaveRights
        User.city = it.loginModel.city
        User.department = it.loginModel.department
        User.empId = it.loginModel.empId
        User.dob = it.loginModel.dob
        User.email = it.loginModel.emailId
        User.phoneNumber = it.loginModel.phonenumber
        User.gender = it.loginModel.gender
        User.position = it.loginModel.position
        User.userType = USER_EMPLOYEE
    }

}