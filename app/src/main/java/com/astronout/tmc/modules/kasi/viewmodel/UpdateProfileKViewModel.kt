package com.astronout.tmc.modules.kasi.viewmodel

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

class UpdateProfileKViewModel(application: Application): AndroidViewModel(application) {

    val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    val _imageFile = MutableLiveData<File>()
    val imageFile: LiveData<File>
        get() = _imageFile

    val _avatarMessage = MutableLiveData<String>()
    val avatarMessage: LiveData<String>
        get() = _avatarMessage

    val _avatarUploaded = MutableLiveData<Boolean>()
    val avatarUploaded: LiveData<Boolean>
        get() = _avatarUploaded

    val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    val _jabatan = MutableLiveData<String>()
    val jabatan: LiveData<String>
        get() = _jabatan

    val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

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

    fun setJabatan(jabatan: String) {
        _jabatan.value = jabatan
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

    fun setAvatarMessage(message: String) {
        _avatarMessage.value = message
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setCity(city: String) {
        _city.value = city
    }

    fun setCountry(country: String) {
        _country.value = country
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

    fun uploadImage(){
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateKasiAvatar(imageFile.value!!, {
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
        remoteRepository.postUpdateKasi(username.value!!, name.value!!, gender.value!!, dob.value!!,
            address.value!!, city.value!!, country.value!!, phoneNumber.value!!, jabatan.value!!,
            User.kasiId, {
                setStatus(it.status)
            }, {
                logDebug("updateProfileKasiError: $it")
            }, {
                setLoading(false)
            })
    }

}