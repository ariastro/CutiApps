package com.astronout.tmc.modules.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private val _firstname = MutableLiveData<String>()
    val firstname: LiveData<String>
        get() = _firstname

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _lastname = MutableLiveData<String>()
    val lastname: LiveData<String>
        get() = _lastname

    private val _annualLeaveRights = MutableLiveData<String>()
    val annualLeaveRights: LiveData<String>
        get() = _annualLeaveRights

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _avatar = MutableLiveData<String?>()
    val avatar: LiveData<String?>
        get() = _avatar

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _position = MutableLiveData<String>()
    val position: LiveData<String>
        get() = _position

    private val _department = MutableLiveData<String>()
    val department: LiveData<String>
        get() = _department

    private val _dob = MutableLiveData<String>()
    val dob: LiveData<String>
        get() = _dob

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _country = MutableLiveData<String>()
    val country: LiveData<String>
        get() = _country

    private val _empId = MutableLiveData<String>()
    val empId: LiveData<String>
        get() = _empId

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getEmployeeById(User.idEmployee, {
            if (it.status) {
                saveToKotpref(it)
                setEmpId(it.getEmployeeByIdModel.empId)
                setFirstname(it.getEmployeeByIdModel.firstName)
                setLastname(it.getEmployeeByIdModel.lastName)
                setEmail(it.getEmployeeByIdModel.emailId)
                setPosition(it.getEmployeeByIdModel.position)
                setDepartment(it.getEmployeeByIdModel.department)
                setDob(it.getEmployeeByIdModel.dob)
                setAvatar(it.getEmployeeByIdModel.avatar!!)
                setGender(it.getEmployeeByIdModel.gender)
                setAddress(it.getEmployeeByIdModel.address)
                setPhoneNumber(it.getEmployeeByIdModel.phonenumber)
                setCity(it.getEmployeeByIdModel.city)
                setCountry(it.getEmployeeByIdModel.country)
                setAnnualLeaveRights(it.getEmployeeByIdModel.annualLeaveRights)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setAnnualLeaveRights(annualLeaveRights: String) {
        _annualLeaveRights.value = annualLeaveRights
    }

    fun setFirstname(firstname: String) {
        _firstname.value = firstname
    }

    fun setAvatar(avatar: String?) {
        _avatar.value = avatar
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLastname(lastname: String) {
        _lastname.value = lastname
    }

    fun setEmpId(empId: String) {
        _empId.value = empId
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setPosition(position: String) {
        _position.value = position
    }

    fun setDepartment(department: String) {
        _department.value = department
    }

    fun setDob(dob: String) {
        _dob.value = dob
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setCity(city: String) {
        _city.value = city
    }

    fun setCountry(country: String) {
        _country.value = country
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