package com.astronout.tmc.modules.auth.admin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.Constants.USER_ADMIN
import com.astronout.tmc.utils.Constants.USER_MANAGER
import com.astronout.tmc.utils.logDebug

class LoginAdminViewModel(application: Application): AndroidViewModel(application) {

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

    val _isSucceed = MutableLiveData<Boolean>()
    val isSucceed: LiveData<Boolean>
        get() = _isSucceed

    val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setLogin(isLogedIn: Boolean) {
        _isLogedIn.value = isLogedIn
    }

    fun setSucceed(isSucceed: Boolean) {
        _isSucceed.value = isSucceed
    }

    fun setMessage(message: String) {
        _message.value = message
    }

    fun loginAdmin() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.postAdminLogin(userName.value!!, password.value!!, {
            if (it.status) {
                setLogin(true)
                User.adminId = it.loginAdminModel.id
                User.adminStatus = it.loginAdminModel.adminStatus
                User.userType = USER_ADMIN
                if (it.loginAdminModel.adminStatus == "1") {
                    User.token = it.loginAdminModel.token
                } else {
                    User.token = ""
                }
            } else {
                setLogin(false)
                setMessage(it.message)
            }
        }, {
            logDebug("ErrorAdminLogin: $it")
        }, {
            setLoading(false)
            if (isLogedIn.value == true) {
                setSucceed(true)
            } else {
                setSucceed(false)
            }
        })
    }

}