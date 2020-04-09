package com.astronout.tmc.modules.leavetype.nonannual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class AddNonAnnualViewModel(application: Application): AndroidViewModel(application) {

    val _no = MutableLiveData<String>()
    val no: LiveData<String>
        get() = _no

    val _leaveType = MutableLiveData<String>()
    val leaveType: LiveData<String>
        get() = _leaveType

    val _rightsGranted = MutableLiveData<String>()
    val rightsGranted: LiveData<String>
        get() = _rightsGranted

    val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setRightsGranted(rightsGranted: String) {
        _rightsGranted.value = rightsGranted
    }

    fun addNonAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postNewNonAnnual(no.value!!, leaveType.value!!, rightsGranted.value!!, description.value!!, {
            setStatus(it.status)
        }, {
            logDebug(it)
        }, {
            setLoading(false)
        })
    }

}