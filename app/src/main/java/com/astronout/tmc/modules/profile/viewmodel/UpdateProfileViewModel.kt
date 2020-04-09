package com.astronout.tmc.modules.profile.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug
import java.io.File

class UpdateProfileViewModel(application: Application): AndroidViewModel(application) {

    val _firstname = MutableLiveData<String>()
    val firstname: LiveData<String>
        get() = _firstname

    val _imageFile = MutableLiveData<File>()
    val imageFile: LiveData<File>
        get() = _imageFile

    val _avatarMessage = MutableLiveData<String>()
    val avatarMessage: LiveData<String>
        get() = _avatarMessage

    val _avatarUploaded = MutableLiveData<Boolean>()
    val avatarUploaded: LiveData<Boolean>
        get() = _avatarUploaded

    val _lastname = MutableLiveData<String>()
    val lastname: LiveData<String>
        get() = _lastname

    val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    val _department = MutableLiveData<String>()
    val department: LiveData<String>
        get() = _department

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

    fun setAvatarUploaded(avatarUploaded: Boolean) {
        _avatarUploaded.value = avatarUploaded
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setDepartment(department: String) {
        _department.value = department
    }

    fun setAvatarMessage(message: String) {
        _avatarMessage.value = message
    }

    fun setDob(dob: String) {
        _dob.value = dob
    }

    fun setImageFile(imageFile: File) {
        _imageFile.value = imageFile
    }

    fun setImagePath(path: String) {
        val imagePath = Uri.parse(path)
        setImageFile(File(imagePath.path!!))
    }

    init {
        setGender(User.gender)
        _firstname.value = User.firstName
        _lastname.value = User.lastName
        _address.value = User.address
        _phoneNumber.value = User.phoneNumber
        setDob(User.dob)
        _city.value = User.city
        _country.value = User.country
        setDepartment(User.department)
    }

    fun uploadImage(){
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateAvatar(imageFile.value!!, {
            setAvatarMessage(it.message)
            setAvatarUploaded(it.status)
        },{
            logDebug("Upload Image Error: $it")
        },{
            setLoading(false)
        })
    }

    fun updateProfile() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateEmployee(User.empId, firstname.value!!, lastname.value!!,
            User.email, gender.value!!, dob.value!!, User.position, department.value!!,
            address.value!!, city.value!!, country.value!!, phoneNumber.value!!,
            User.annualLeaveRights, User.idEmployee, {
                if (it.status) {
                    setStatus(true)
                } else {
                    setStatus(false)
                }
            }, {
                logDebug("updateProfileError: $it")
            }, {
                setLoading(false)
            })
    }

}