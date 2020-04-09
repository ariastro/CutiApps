package com.astronout.tmc.modules.employees.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.PRIA
import com.astronout.tmc.utils.logDebug

class AddEmployeeViewModel(application: Application): AndroidViewModel(application) {

    val _empId = MutableLiveData<String>()
    val empId: LiveData<String>
        get() = _empId

    val _firstname = MutableLiveData<String>()
    val firstname: LiveData<String>
        get() = _firstname

    val _lastname = MutableLiveData<String>()
    val lastname: LiveData<String>
        get() = _lastname

    val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    val _department = MutableLiveData<String>()
    val department: LiveData<String>
        get() = _department

    val _position = MutableLiveData<String>()
    val position: LiveData<String>
        get() = _position

    val _dob = MutableLiveData<String>()
    val dob: LiveData<String>
        get() = _dob

    val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    val _country = MutableLiveData<String>()
    val country: LiveData<String>
        get() = _country

    val _annualLeaveRights = MutableLiveData<String>()
    val annualLeaveRights: LiveData<String>
        get() = _annualLeaveRights

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setDob(dob: String) {
        _dob.value = dob
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setDepartment(department: String) {
        _department.value = department
    }

    fun addEmployee() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postNewEmployee(empId.value!!, firstname.value!!, lastname.value!!, email.value!!,
            password.value!!, gender.value!!, dob.value!!, position.value!!, department.value!!, address.value!!,
            city.value!!, country.value!!, phoneNumber.value!!, annualLeaveRights.value!!, {
            setStatus(it.status)
        }, {
            logDebug(it)
        }, {
            setLoading(false)
        })
    }

    init {
        setGender(PRIA)
    }

}