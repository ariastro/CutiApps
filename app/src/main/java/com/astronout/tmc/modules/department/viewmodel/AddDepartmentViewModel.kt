package com.astronout.tmc.modules.department.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class AddDepartmentViewModel(application: Application): AndroidViewModel(application) {

    val _departmentName = MutableLiveData<String>()
    val departmentName: LiveData<String>
        get() = _departmentName

    val _departmentShortName = MutableLiveData<String>()
    val departmentShortName: LiveData<String>
        get() = _departmentShortName

    val _departmentCode = MutableLiveData<String>()
    val departmentCode: LiveData<String>
        get() = _departmentCode

    val _departmentId = MutableLiveData<String>()
    val departmentId: LiveData<String>
        get() = _departmentId

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

    fun setDepartmentName(departmentName: String) {
        _departmentName.value = departmentName
    }

    fun setDepartmentShortName(departmentShortName: String) {
        _departmentShortName.value = departmentShortName
    }

    fun setDepartmentCode(departmentCode: String) {
        _departmentCode.value = departmentCode
    }

    fun setDepartmentId(departmentId: String) {
        _departmentId.value = departmentId
    }

    fun addDepartment() {
        setLoading(true)
        val remoteRepository = RemoteRepository(viewModelScope)
        remoteRepository.postNewDepartment(departmentName.value!!, departmentShortName.value!!, departmentCode.value!!, {
            if (it.status) {
                setStatus(true)
            } else {
                setStatus(false)
            }
        }, {
            logDebug("#addDepartmentError : $it")
        }, {
            setLoading(false)
        })
    }

}