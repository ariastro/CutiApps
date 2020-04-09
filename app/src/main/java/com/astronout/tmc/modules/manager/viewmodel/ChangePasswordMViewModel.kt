package com.astronout.tmc.modules.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug
import kotlinx.coroutines.Dispatchers

class ChangePasswordMViewModel(application: Application): AndroidViewModel(application) {

    val _oldPassword = MutableLiveData<String>()
    val oldPassword: LiveData<String>
        get() = _oldPassword

    val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String>
        get() = _newPassword

    val _confirmNewPassword = MutableLiveData<String>()
    val confirmNewPassword: LiveData<String>
        get() = _confirmNewPassword

    private val _onComplete = MutableLiveData<Boolean>()
    val onComplete: LiveData<Boolean>
        get() = _onComplete

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun changePassword() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postChangePasswordManager(oldPassword.value!!, newPassword.value!!, User.managerId, {
            onSaveComplete(it.status)
        }, {
            logDebug(it)
        }, {
            setLoading(false)
        })
    }

    fun setOldPassword(oldPassword: String) {
        _oldPassword.value = oldPassword
    }

    fun setNewPassword(newPassword: String) {
        _newPassword.value = newPassword
    }

    fun setConfirmNewPassword(confirmNewPassword: String) {
        _confirmNewPassword.value = confirmNewPassword
    }

    fun onSaveComplete(complete: Boolean) {
        _onComplete.value = complete
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

}