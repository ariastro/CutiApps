package com.astronout.tmc.modules.leavetype.annual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class UpdateAnnualViewModel(application: Application): AndroidViewModel(application) {

    val _no = MutableLiveData<String>()
    val no: LiveData<String>
        get() = _no

    val _leaveType = MutableLiveData<String>()
    val leaveType: LiveData<String>
        get() = _leaveType

    val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun setDescription(description: String) {
        _description.value = description
    }

    fun setNo(no: String) {
        _no.value = no
    }

    fun setLeaveType(leaveType: String) {
        _leaveType.value = leaveType
    }

    fun setStatus(status: Boolean) {
        _status.value = status
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun updateAnnual() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postUpdateAnnual(no.value!!, leaveType.value!!, description.value!!, id.value!!, {
            if (it.status) {
                setStatus(true)
            } else {
                setStatus(false)
            }
        }, {
            logDebug("updateAnnual # ErrorMessage: $it")
        }, {
            setLoading(false)
        })
    }

}