package com.astronout.tmc.modules.admin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.PRIA
import com.astronout.tmc.utils.logDebug

class AddAdminViewModel(application: Application): AndroidViewModel(application) {

    val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

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

    fun addAdmin() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postNewAdmin(username.value!!, password.value!!, name.value!!,
            gender.value!!, dob.value!!, address.value!!, city.value!!, country.value!!,
            phoneNumber.value!!,{
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